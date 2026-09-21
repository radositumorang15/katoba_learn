package id.kotoba.app.controller

import id.kotoba.app.model.Level
import id.kotoba.app.model.UserProfile
import id.kotoba.app.model.repository.AuthRepository
import id.kotoba.app.model.repository.ProgressRepository
import id.kotoba.app.model.repository.SrsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class LibraryTab {
    ALL_LIBRARY,
    COMPLETED
}

enum class ActionMenu {
    ALL_CLASSES,
    MY_COURSES,
    MY_SHELF,
    MY_PROGRESS
}

class HomeController(
    private val progressRepo: ProgressRepository,
    private val authRepo: AuthRepository,
    private val srsRepo: SrsRepository,
    private val navController: NavigationController
) {
    val currentUser: StateFlow<UserProfile?> = authRepo.currentUser
    val levels: StateFlow<List<Level>> = progressRepo.levels
    val dueReviewCount: StateFlow<Int> = srsRepo.dueCount
    val masteredCount: StateFlow<Int> = srsRepo.masteredCount

    private val _selectedLibraryTab = MutableStateFlow(LibraryTab.ALL_LIBRARY)
    val selectedLibraryTab: StateFlow<LibraryTab> = _selectedLibraryTab.asStateFlow()

    private val _selectedMenuNotice = MutableStateFlow<String?>(null)
    val selectedMenuNotice: StateFlow<String?> = _selectedMenuNotice.asStateFlow()

    fun selectLibraryTab(tab: LibraryTab) {
        _selectedLibraryTab.value = tab
    }

    fun onActionMenuClick(menu: ActionMenu) {
        when (menu) {
            ActionMenu.ALL_CLASSES -> navController.navigateTo(Screen.Roadmap)
            ActionMenu.MY_COURSES -> navController.navigateTo(Screen.Learning("lvl_1"))
            ActionMenu.MY_SHELF -> _selectedMenuNotice.value = "Fitur My Shelf: Koleksi buku kana dan catatan anime tersimpan!"
            ActionMenu.MY_PROGRESS -> navController.navigateTo(Screen.Profile)
        }
    }

    fun dismissNotice() {
        _selectedMenuNotice.value = null
    }

    fun startDailyReview() {
        navController.navigateTo(Screen.ReviewSession)
    }

    fun startLearning(levelId: String) {
        val level = progressRepo.getLevelById(levelId)
        if (level?.isUnlocked == true) {
            navController.navigateTo(Screen.Learning(levelId))
        }
    }

    fun startQuiz(levelId: String) {
        val level = progressRepo.getLevelById(levelId)
        if (level?.isUnlocked == true) {
            navController.navigateTo(Screen.Quiz(levelId))
        }
    }
}
