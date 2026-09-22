package id.kotoba.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kotoba.app.controller.AuthController
import id.kotoba.app.controller.GoalQuizController
import id.kotoba.app.controller.HomeController
import id.kotoba.app.controller.LearningController
import id.kotoba.app.controller.NavigationController
import id.kotoba.app.controller.ProfileController
import id.kotoba.app.controller.QuizController
import id.kotoba.app.controller.ReviewSessionController
import id.kotoba.app.controller.RoadmapController
import id.kotoba.app.controller.Screen
import id.kotoba.app.model.repository.AuthRepository
import id.kotoba.app.model.repository.GoalsRepository
import id.kotoba.app.model.repository.ProgressRepository
import id.kotoba.app.model.repository.SrsRepository
import id.kotoba.app.platform.rememberAudioTtsEngine
import id.kotoba.app.view.components.BottomNavBar
import id.kotoba.app.view.screens.AuthScreen
import id.kotoba.app.view.screens.GoalQuizScreen
import id.kotoba.app.view.screens.HomeScreen
import id.kotoba.app.view.screens.LearningScreen
import id.kotoba.app.view.screens.OnboardingScreen
import id.kotoba.app.view.screens.ProfileScreen
import id.kotoba.app.view.screens.QuizScreen
import id.kotoba.app.view.screens.ReviewSessionScreen
import id.kotoba.app.view.screens.RoadmapScreen
import id.kotoba.app.view.screens.SplashScreen
import id.kotoba.app.view.theme.KotobaTheme

import id.kotoba.app.controller.AiConversationController
import id.kotoba.app.controller.GlossaryController
import id.kotoba.app.model.repository.GeminiRepository
import id.kotoba.app.view.screens.AiConversationScreen
import id.kotoba.app.view.screens.GlossaryScreen

/**
 * Container holding all lazy-initialized repositories.
 * Repositories are initialized inside LaunchedEffect so that the initial
 * frame renders immediately without layout stalls.
 */
private class AppState(
    val progressRepository: ProgressRepository,
    val authRepository: AuthRepository,
    val srsRepository: SrsRepository,
    val goalsRepository: GoalsRepository,
    val geminiRepository: GeminiRepository
)

@Composable
fun App() {
    KotobaTheme {
        // Compose renders the initial frame (SplashScreen) first.
        // Initialization runs in LaunchedEffect on first frame.
        var appState by remember { mutableStateOf<AppState?>(null) }
        var initError by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(Unit) {
            runCatching {
                AppState(
                    progressRepository = ProgressRepository(),
                    authRepository = AuthRepository(),
                    srsRepository = SrsRepository(),
                    goalsRepository = GoalsRepository(),
                    geminiRepository = GeminiRepository()
                )
            }.onSuccess { state ->
                appState = state
            }.onFailure { e ->
                println("💥 [App] Repository init failed: ${e.message}\n${e.stackTraceToString()}")
                initError = e.message ?: e::class.simpleName ?: "Unknown error"
            }
        }

        // Show error screen if init failed (helps debug without crash)
        val error = initError
        if (error != null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "⚠️ Startup Error:\n$error\n\nPlease reinstall the app.",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }
            return@KotobaTheme
        }

        // Show native splash while repositories are loading
        val state = appState
        if (state == null) {
            SplashScreen(onFinished = { /* wait for state */ })
            return@KotobaTheme
        }

        // Repositories ready – build the full app
        AppContent(state)
    }
}

@Composable
private fun AppContent(state: AppState) {
    val progressRepository = state.progressRepository
    val authRepository = state.authRepository
    val srsRepository = state.srsRepository
    val goalsRepository = state.goalsRepository
    val geminiRepository = state.geminiRepository

    // Audio TTS Engine
    val ttsEngine = rememberAudioTtsEngine()

    // Navigation Controller
    val navController = remember { NavigationController() }
    val currentScreen by navController.currentScreen.collectAsState()
    val selectedTab by navController.selectedTab.collectAsState()
    val isLoggedIn by authRepository.isLoggedIn.collectAsState()

    // Controllers
    val homeController = remember(progressRepository, authRepository, srsRepository, navController) {
        HomeController(progressRepository, authRepository, srsRepository, navController)
    }
    val roadmapController = remember(progressRepository, navController) {
        RoadmapController(progressRepository, navController)
    }
    val profileController = remember(authRepository, progressRepository, navController) {
        ProfileController(authRepository, progressRepository, navController)
    }
    val authController = remember(authRepository, goalsRepository, navController) {
        AuthController(authRepository, navController, goalsRepository)
    }
    val goalQuizController = remember(goalsRepository, progressRepository, authRepository, navController) {
        GoalQuizController(goalsRepository, progressRepository, authRepository, navController)
    }
    val glossaryController = remember(geminiRepository, navController) {
        GlossaryController(geminiRepository, navController)
    }

    when (val screen = currentScreen) {
        is Screen.Splash -> {
            SplashScreen(
                onFinished = {
                    if (isLoggedIn) {
                        val user = authRepository.currentUser.value
                        if (goalsRepository.hasCompletedGoalQuiz()) {
                            navController.navigateTo(Screen.Home, clearStack = true)
                        } else if (user != null && user.email.isNotBlank()) {
                            // Existing registered user shouldn't be trapped in goal quiz
                            goalsRepository.markDefaultCompleted(user.id)
                            navController.navigateTo(Screen.Home, clearStack = true)
                        } else {
                            navController.navigateTo(Screen.GoalQuiz, clearStack = true)
                        }
                    } else {
                        navController.navigateTo(Screen.Onboarding, clearStack = true)
                    }
                }
            )
        }

        is Screen.Onboarding -> {
            OnboardingScreen(
                onStartAdventure = {
                    navController.navigateTo(Screen.Auth)
                }
            )
        }

        is Screen.Auth -> {
            AuthScreen(controller = authController)
        }

        is Screen.GoalQuiz -> {
            GoalQuizScreen(controller = goalQuizController)
        }

        is Screen.ReviewSession -> {
            val reviewSessionController = remember(srsRepository, authRepository, navController, ttsEngine) {
                ReviewSessionController(
                    srsRepo = srsRepository,
                    authRepo = authRepository,
                    navController = navController,
                    ttsEngine = ttsEngine
                )
            }
            ReviewSessionScreen(controller = reviewSessionController)
        }

        is Screen.AiConversation -> {
            val aiConversationController = remember(geminiRepository, srsRepository, navController, ttsEngine) {
                AiConversationController(
                    geminiRepo = geminiRepository,
                    srsRepo = srsRepository,
                    navController = navController,
                    ttsEngine = ttsEngine
                )
            }
            AiConversationScreen(controller = aiConversationController)
        }

        else -> {
            // Main Application with Bottom Navigation
            val showBottomBar = screen is Screen.Home ||
                    screen is Screen.Roadmap ||
                    screen is Screen.Profile ||
                    screen is Screen.Glossary

            Scaffold(
                bottomBar = {
                    if (showBottomBar) {
                        BottomNavBar(
                            selectedTab = selectedTab,
                            onTabSelected = { navController.selectTab(it) }
                        )
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (screen) {
                        is Screen.Home -> {
                            HomeScreen(controller = homeController)
                        }

                        is Screen.Learning -> {
                            val learningController = remember(screen.levelId) {
                                LearningController(
                                    progressRepo = progressRepository,
                                    navController = navController,
                                    levelId = screen.levelId,
                                    ttsEngine = ttsEngine,
                                    srsRepo = srsRepository,
                                    authRepo = authRepository,
                                    geminiRepo = geminiRepository
                                )
                            }
                            LearningScreen(controller = learningController)
                        }

                        is Screen.Quiz -> {
                            val quizController = remember(screen.levelId) {
                                QuizController(
                                    levelId = screen.levelId,
                                    progressRepo = progressRepository,
                                    authRepo = authRepository,
                                    navController = navController,
                                    ttsEngine = ttsEngine,
                                    geminiRepo = geminiRepository
                                )
                            }
                            QuizScreen(controller = quizController)
                        }

                        is Screen.Roadmap -> {
                            RoadmapScreen(controller = roadmapController)
                        }

                        is Screen.Glossary -> {
                            GlossaryScreen(controller = glossaryController)
                        }

                        is Screen.Profile -> {
                            ProfileScreen(controller = profileController)
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}
