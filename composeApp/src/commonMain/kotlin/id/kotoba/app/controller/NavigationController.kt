package id.kotoba.app.controller

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class Screen {
    data object Splash : Screen()
    data object Onboarding : Screen()
    data object Auth : Screen()
    data object GoalQuiz : Screen()
    data object ReviewSession : Screen()
    data object Home : Screen()
    data class Learning(val levelId: String) : Screen()
    data class Quiz(val levelId: String) : Screen()
    data object Roadmap : Screen()
    data object Profile : Screen()
    data object AiConversation : Screen()
    data object Glossary : Screen()
}

enum class BottomTab {
    HOME,
    ROADMAP,
    LEARN,
    GLOSSARY,
    PROFILE
}

open class NavigationController(
    var onNavigateCallback: ((Screen, Boolean) -> Unit)? = null,
    var onGoBackCallback: (() -> Boolean)? = null,
    var onSelectTabCallback: ((BottomTab) -> Unit)? = null
) {
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Splash)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _selectedTab = MutableStateFlow(BottomTab.HOME)
    val selectedTab: StateFlow<BottomTab> = _selectedTab.asStateFlow()

    private val backStack = mutableListOf<Screen>()

    private fun updateSelectedTab(screen: Screen) {
        when (screen) {
            is Screen.Home -> _selectedTab.value = BottomTab.HOME
            is Screen.Roadmap -> _selectedTab.value = BottomTab.ROADMAP
            is Screen.Learning -> _selectedTab.value = BottomTab.LEARN
            is Screen.Glossary -> _selectedTab.value = BottomTab.GLOSSARY
            is Screen.Profile -> _selectedTab.value = BottomTab.PROFILE
            else -> {}
        }
    }

    open fun navigateTo(screen: Screen, clearStack: Boolean = false) {
        val callback = onNavigateCallback
        if (callback != null) {
            callback(screen, clearStack)
            _currentScreen.value = screen
            updateSelectedTab(screen)
            return
        }
        if (clearStack) {
            backStack.clear()
        } else {
            backStack.add(_currentScreen.value)
        }
        _currentScreen.value = screen
        updateSelectedTab(screen)
    }

    open fun selectTab(tab: BottomTab) {
        val callback = onSelectTabCallback
        if (callback != null) {
            callback(tab)
            _selectedTab.value = tab
            return
        }
        _selectedTab.value = tab
        when (tab) {
            BottomTab.HOME -> navigateTo(Screen.Home)
            BottomTab.ROADMAP -> navigateTo(Screen.Roadmap)
            BottomTab.LEARN -> navigateTo(Screen.Learning("lvl_1"))
            BottomTab.GLOSSARY -> navigateTo(Screen.Glossary)
            BottomTab.PROFILE -> navigateTo(Screen.Profile)
        }
    }

    open fun goBack(): Boolean {
        val callback = onGoBackCallback
        if (callback != null) {
            return callback()
        }
        if (backStack.isNotEmpty()) {
            val prev = backStack.removeAt(backStack.size - 1)
            val targetScreen = if (prev is Screen.Splash || prev is Screen.Onboarding || prev is Screen.Auth) {
                Screen.Home
            } else {
                prev
            }
            _currentScreen.value = targetScreen
            updateSelectedTab(targetScreen)
            return true
        } else if (_currentScreen.value !is Screen.Home) {
            // Fallback aman jika backStack kosong dan user tidak di Home
            _currentScreen.value = Screen.Home
            _selectedTab.value = BottomTab.HOME
            return true
        }
        return false
    }
}
