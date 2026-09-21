package id.kotoba.app.controller

import id.kotoba.app.model.Level
import id.kotoba.app.model.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoadmapController(
    private val progressRepo: ProgressRepository,
    private val navController: NavigationController
) {
    val levels: StateFlow<List<Level>> = progressRepo.levels

    private val _lockedNotice = MutableStateFlow<String?>(null)
    val lockedNotice: StateFlow<String?> = _lockedNotice.asStateFlow()

    fun onLevelClick(level: Level) {
        if (level.isUnlocked) {
            navController.navigateTo(Screen.Learning(level.id))
        } else {
            _lockedNotice.value = "Level ${level.levelNumber} (${level.title}) masih terkunci! Selesaikan kuis level sebelumnya dengan skor minimal 80%."
        }
    }

    fun dismissNotice() {
        _lockedNotice.value = null
    }

    fun onStartQuiz(levelId: String) {
        navController.navigateTo(Screen.Quiz(levelId))
    }
}
