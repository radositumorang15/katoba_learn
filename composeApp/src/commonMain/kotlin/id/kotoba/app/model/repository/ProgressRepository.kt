package id.kotoba.app.model.repository

import id.kotoba.app.model.Level
import id.kotoba.app.model.ProgressStatus
import id.kotoba.app.model.UserProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProgressRepository {

    private val _levels = MutableStateFlow<List<Level>>(KanaRepository.allLevels)
    val levels: StateFlow<List<Level>> = _levels.asStateFlow()

    private val _progressMap = MutableStateFlow<Map<String, UserProgress>>(
        mapOf(
            "lvl_1" to UserProgress("lvl_1", ProgressStatus.IN_PROGRESS, highScore = 0)
        )
    )
    val progressMap: StateFlow<Map<String, UserProgress>> = _progressMap.asStateFlow()

    private val _masteredKanaIds = MutableStateFlow<Set<String>>(emptySet())
    val masteredKanaIds: StateFlow<Set<String>> = _masteredKanaIds.asStateFlow()

    fun isLevelUnlocked(levelId: String): Boolean {
        return _levels.value.find { it.id == levelId }?.isUnlocked == true
    }

    fun getLevelById(levelId: String): Level? {
        return _levels.value.find { it.id == levelId }
    }

    fun markKanaMastered(kanaId: String, mastered: Boolean = true) {
        val current = _masteredKanaIds.value.toMutableSet()
        if (mastered) {
            current.add(kanaId)
        } else {
            current.remove(kanaId)
        }
        _masteredKanaIds.value = current
    }

    fun completeQuiz(levelId: String, scorePercentage: Int, earnedXp: Int): Boolean {
        val isPassed = scorePercentage >= 80
        val currentList = _levels.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == levelId }
        
        if (index != -1) {
            val currentLevel = currentList[index]
            if (isPassed) {
                currentList[index] = currentLevel.copy(isCompleted = true)
                // Unlock next level
                if (index + 1 < currentList.size) {
                    val nextLevel = currentList[index + 1]
                    currentList[index + 1] = nextLevel.copy(isUnlocked = true)
                }
            }
            _levels.value = currentList
        }

        // Update progress map
        val currentMap = _progressMap.value.toMutableMap()
        val existing = currentMap[levelId] ?: UserProgress(levelId, ProgressStatus.IN_PROGRESS)
        currentMap[levelId] = existing.copy(
            status = if (isPassed) ProgressStatus.COMPLETED else ProgressStatus.IN_PROGRESS,
            highScore = maxOf(existing.highScore, scorePercentage),
            isQuizPassed = isPassed || existing.isQuizPassed
        )
        _progressMap.value = currentMap

        return isPassed
    }

    fun unlockUpToLevel(levelId: String) {
        val currentList = _levels.value.toMutableList()
        val targetIndex = currentList.indexOfFirst { it.id == levelId }
        if (targetIndex != -1) {
            for (i in 0..targetIndex) {
                currentList[i] = currentList[i].copy(isUnlocked = true)
            }
            _levels.value = currentList
        }
    }

    fun resetProgress() {
        _levels.value = KanaRepository.allLevels
        _progressMap.value = mapOf(
            "lvl_1" to UserProgress("lvl_1", ProgressStatus.IN_PROGRESS, highScore = 0)
        )
        _masteredKanaIds.value = emptySet()
    }
}
