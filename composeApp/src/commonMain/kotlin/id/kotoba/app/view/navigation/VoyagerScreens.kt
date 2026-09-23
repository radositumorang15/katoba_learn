package id.kotoba.app.view.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import id.kotoba.app.controller.ActionMenu
import id.kotoba.app.controller.AiConversationController
import id.kotoba.app.controller.AuthController
import id.kotoba.app.controller.BottomTab
import id.kotoba.app.controller.GlossaryController
import id.kotoba.app.controller.GoalQuizController
import id.kotoba.app.controller.HomeController
import id.kotoba.app.controller.LearningController
import id.kotoba.app.controller.NavigationController
import id.kotoba.app.controller.ProfileController
import id.kotoba.app.controller.QuizController
import id.kotoba.app.controller.ReviewSessionController
import id.kotoba.app.controller.RoadmapController
import id.kotoba.app.controller.Screen as AppScreen
import id.kotoba.app.view.components.OptimizedBottomNavBar
import id.kotoba.app.view.screens.AiConversationScreen
import id.kotoba.app.view.screens.AuthScreen
import id.kotoba.app.view.screens.GlossaryScreen
import id.kotoba.app.view.screens.GoalQuizScreen
import id.kotoba.app.view.screens.HomeScreen
import id.kotoba.app.view.screens.LearningScreen
import id.kotoba.app.view.screens.OnboardingScreen
import id.kotoba.app.view.screens.ProfileScreen
import id.kotoba.app.view.screens.QuizScreen
import id.kotoba.app.view.screens.ReviewSessionScreen
import id.kotoba.app.view.screens.RoadmapScreen
import id.kotoba.app.view.screens.SplashScreen

fun mapAppScreenToVoyager(screen: AppScreen): Screen {
    return when (screen) {
        is AppScreen.Splash -> SplashVoyagerScreen
        is AppScreen.Onboarding -> OnboardingVoyagerScreen
        is AppScreen.Auth -> AuthVoyagerScreen
        is AppScreen.GoalQuiz -> GoalQuizVoyagerScreen
        is AppScreen.Home -> HomeVoyagerScreen
        is AppScreen.Learning -> LearningVoyagerScreen(screen.levelId)
        is AppScreen.Quiz -> QuizVoyagerScreen(screen.levelId)
        is AppScreen.Roadmap -> RoadmapVoyagerScreen
        is AppScreen.Glossary -> GlossaryVoyagerScreen
        is AppScreen.Profile -> ProfileVoyagerScreen
        is AppScreen.ReviewSession -> ReviewSessionVoyagerScreen
        is AppScreen.AiConversation -> AiConversationVoyagerScreen
    }
}

@Composable
fun rememberVoyagerNavController(
    navigator: Navigator,
    rootNavigator: Navigator? = null
): NavigationController {
    val tabNavigator = runCatching { LocalTabNavigator.current }.getOrNull()

    return remember(navigator, tabNavigator, rootNavigator) {
        NavigationController(
            onNavigateCallback = { screen, clearStack ->
                val targetVoyager = mapAppScreenToVoyager(screen)
                if (clearStack) {
                    when (screen) {
                        is AppScreen.Home -> {
                            if (tabNavigator != null) {
                                tabNavigator.current = HomeTab
                                navigator.popUntilRoot()
                            } else {
                                rootNavigator?.replaceAll(MainTabsVoyagerScreen)
                                    ?: navigator.replaceAll(MainTabsVoyagerScreen)
                            }
                        }
                        is AppScreen.Auth -> {
                            rootNavigator?.replaceAll(AuthVoyagerScreen)
                                ?: navigator.replaceAll(AuthVoyagerScreen)
                        }
                        is AppScreen.Onboarding -> {
                            rootNavigator?.replaceAll(OnboardingVoyagerScreen)
                                ?: navigator.replaceAll(OnboardingVoyagerScreen)
                        }
                        is AppScreen.GoalQuiz -> {
                            rootNavigator?.replaceAll(GoalQuizVoyagerScreen)
                                ?: navigator.replaceAll(GoalQuizVoyagerScreen)
                        }
                        else -> {
                            navigator.replaceAll(targetVoyager)
                        }
                    }
                } else {
                    navigator.push(targetVoyager)
                }
            },
            onGoBackCallback = {
                if (navigator.canPop) {
                    navigator.pop()
                    true
                } else if (tabNavigator != null && tabNavigator.current != HomeTab) {
                    tabNavigator.current = HomeTab
                    true
                } else if (rootNavigator != null && rootNavigator.canPop) {
                    rootNavigator.pop()
                    true
                } else {
                    false
                }
            },
            onSelectTabCallback = { tab ->
                tabNavigator?.let {
                    it.current = when (tab) {
                        BottomTab.HOME -> HomeTab
                        BottomTab.ROADMAP -> RoadmapTab
                        BottomTab.LEARN -> LearnTab
                        BottomTab.GLOSSARY -> GlossaryTab
                        BottomTab.PROFILE -> ProfileTab
                    }
                }
            }
        )
    }
}

// MARK: - Root / Fullscreen Voyager Screens

object SplashVoyagerScreen : Screen {
    @Composable
    override fun Content() {
        val appState = LocalAppState.current
        val navigator = LocalNavigator.currentOrThrow
        val isLoggedIn by appState.authRepository.isLoggedIn.collectAsState()

        SplashScreen(
            onFinished = {
                if (isLoggedIn) {
                    val user = appState.authRepository.currentUser.value
                    if (appState.goalsRepository.hasCompletedGoalQuiz()) {
                        navigator.replaceAll(MainTabsVoyagerScreen)
                    } else if (user != null && user.email.isNotBlank()) {
                        appState.goalsRepository.markDefaultCompleted(user.id)
                        navigator.replaceAll(MainTabsVoyagerScreen)
                    } else {
                        navigator.replaceAll(GoalQuizVoyagerScreen)
                    }
                } else {
                    navigator.replaceAll(OnboardingVoyagerScreen)
                }
            }
        )
    }
}

object OnboardingVoyagerScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        OnboardingScreen(
            onStartAdventure = {
                navigator.push(AuthVoyagerScreen)
            }
        )
    }
}

object AuthVoyagerScreen : Screen {
    @Composable
    override fun Content() {
        val appState = LocalAppState.current
        val navigator = LocalNavigator.currentOrThrow
        val navController = rememberVoyagerNavController(navigator)
        val authController = remember(appState, navController) {
            AuthController(appState.authRepository, navController, appState.goalsRepository)
        }
        AuthScreen(controller = authController)
    }
}

object GoalQuizVoyagerScreen : Screen {
    @Composable
    override fun Content() {
        val appState = LocalAppState.current
        val navigator = LocalNavigator.currentOrThrow
        val navController = rememberVoyagerNavController(navigator)
        val goalQuizController = remember(appState, navController) {
            GoalQuizController(
                goalsRepo = appState.goalsRepository,
                progressRepo = appState.progressRepository,
                authRepo = appState.authRepository,
                navController = navController
            )
        }
        GoalQuizScreen(controller = goalQuizController)
    }
}

// MARK: - Main Tabs Voyager Screen with TabNavigator & BottomNavBar

object MainTabsVoyagerScreen : Screen {
    @Composable
    override fun Content() {
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        TabNavigator(HomeTab) {
            Scaffold(
                bottomBar = {
                    OptimizedBottomNavBar()
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            focusManager.clearFocus(force = true)
                            keyboardController?.hide()
                        }
                ) {
                    CurrentTab()
                }
            }
        }
    }
}

// MARK: - Nested Tab Screens

object HomeVoyagerScreen : Screen {
    @Composable
    override fun Content() {
        val appState = LocalAppState.current
        val navigator = LocalNavigator.currentOrThrow
        val rootNavigator = LocalRootNavigator.current
        val navController = rememberVoyagerNavController(navigator, rootNavigator)

        val homeController = remember(appState, navController) {
            HomeController(
                progressRepo = appState.progressRepository,
                authRepo = appState.authRepository,
                srsRepo = appState.srsRepository,
                navController = navController
            )
        }
        HomeScreen(controller = homeController)
    }
}

data class LearningVoyagerScreen(val levelId: String = "lvl_1") : Screen {
    @Composable
    override fun Content() {
        val appState = LocalAppState.current
        val ttsEngine = LocalAudioTtsEngine.current
        val navigator = LocalNavigator.currentOrThrow
        val rootNavigator = LocalRootNavigator.current
        val navController = rememberVoyagerNavController(navigator, rootNavigator)

        val learningController = remember(levelId, appState, navController) {
            LearningController(
                progressRepo = appState.progressRepository,
                navController = navController,
                levelId = levelId,
                ttsEngine = ttsEngine,
                srsRepo = appState.srsRepository,
                authRepo = appState.authRepository,
                geminiRepo = appState.geminiRepository
            )
        }
        LearningScreen(controller = learningController)
    }
}

data class QuizVoyagerScreen(val levelId: String) : Screen {
    @Composable
    override fun Content() {
        val appState = LocalAppState.current
        val ttsEngine = LocalAudioTtsEngine.current
        val navigator = LocalNavigator.currentOrThrow
        val rootNavigator = LocalRootNavigator.current
        val navController = rememberVoyagerNavController(navigator, rootNavigator)

        val quizController = remember(levelId, appState, navController) {
            QuizController(
                levelId = levelId,
                progressRepo = appState.progressRepository,
                authRepo = appState.authRepository,
                navController = navController,
                ttsEngine = ttsEngine,
                geminiRepo = appState.geminiRepository
            )
        }
        QuizScreen(controller = quizController)
    }
}

object RoadmapVoyagerScreen : Screen {
    @Composable
    override fun Content() {
        val appState = LocalAppState.current
        val navigator = LocalNavigator.currentOrThrow
        val rootNavigator = LocalRootNavigator.current
        val navController = rememberVoyagerNavController(navigator, rootNavigator)

        val roadmapController = remember(appState, navController) {
            RoadmapController(
                progressRepo = appState.progressRepository,
                navController = navController
            )
        }
        RoadmapScreen(controller = roadmapController)
    }
}

object GlossaryVoyagerScreen : Screen {
    @Composable
    override fun Content() {
        val appState = LocalAppState.current
        val navigator = LocalNavigator.currentOrThrow
        val rootNavigator = LocalRootNavigator.current
        val navController = rememberVoyagerNavController(navigator, rootNavigator)
        val ttsEngine = LocalAudioTtsEngine.current

        val glossaryController = remember(appState, navController, ttsEngine) {
            GlossaryController(
                geminiRepo = appState.geminiRepository,
                navController = navController,
                ttsEngine = ttsEngine
            )
        }
        GlossaryScreen(controller = glossaryController)
    }
}

object ProfileVoyagerScreen : Screen {
    @Composable
    override fun Content() {
        val appState = LocalAppState.current
        val navigator = LocalNavigator.currentOrThrow
        val rootNavigator = LocalRootNavigator.current
        val navController = rememberVoyagerNavController(navigator, rootNavigator)

        val profileController = remember(appState, navController) {
            ProfileController(
                authRepo = appState.authRepository,
                progressRepo = appState.progressRepository,
                navController = navController
            )
        }
        ProfileScreen(controller = profileController)
    }
}

object ReviewSessionVoyagerScreen : Screen {
    @Composable
    override fun Content() {
        val appState = LocalAppState.current
        val ttsEngine = LocalAudioTtsEngine.current
        val navigator = LocalNavigator.currentOrThrow
        val rootNavigator = LocalRootNavigator.current
        val navController = rememberVoyagerNavController(navigator, rootNavigator)

        val reviewSessionController = remember(appState, navController) {
            ReviewSessionController(
                srsRepo = appState.srsRepository,
                authRepo = appState.authRepository,
                navController = navController,
                ttsEngine = ttsEngine
            )
        }
        ReviewSessionScreen(controller = reviewSessionController)
    }
}

object AiConversationVoyagerScreen : Screen {
    @Composable
    override fun Content() {
        val appState = LocalAppState.current
        val ttsEngine = LocalAudioTtsEngine.current
        val navigator = LocalNavigator.currentOrThrow
        val rootNavigator = LocalRootNavigator.current
        val navController = rememberVoyagerNavController(navigator, rootNavigator)

        val aiConversationController = remember(appState, navController) {
            AiConversationController(
                geminiRepo = appState.geminiRepository,
                srsRepo = appState.srsRepository,
                navController = navController,
                ttsEngine = ttsEngine
            )
        }
        AiConversationScreen(controller = aiConversationController)
    }
}
