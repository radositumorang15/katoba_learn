package id.kotoba.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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

@Composable
fun App() {
    KotobaTheme {
        // Model Repositories
        val progressRepository = remember { ProgressRepository() }
        val authRepository = remember { AuthRepository() }
        val srsRepository = remember { SrsRepository() }
        val goalsRepository = remember { GoalsRepository() }

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
        val authController = remember(authRepository, navController) {
            AuthController(authRepository, navController)
        }
        val goalQuizController = remember(goalsRepository, progressRepository, authRepository, navController) {
            GoalQuizController(goalsRepository, progressRepository, authRepository, navController)
        }

        when (val screen = currentScreen) {
            is Screen.Splash -> {
                SplashScreen(
                    onFinished = {
                        if (isLoggedIn) {
                            if (goalsRepository.hasCompletedGoalQuiz()) {
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

            else -> {
                // Main Application with Bottom Navigation
                val showBottomBar = screen is Screen.Home ||
                        screen is Screen.Roadmap ||
                        screen is Screen.Profile

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
                                        authRepo = authRepository
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
                                        ttsEngine = ttsEngine
                                    )
                                }
                                QuizScreen(controller = quizController)
                            }

                            is Screen.Roadmap -> {
                                RoadmapScreen(controller = roadmapController)
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
}
