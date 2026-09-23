package id.kotoba.app.model.repository

import id.kotoba.app.model.Level
import id.kotoba.app.model.ProgressStatus
import id.kotoba.app.model.StoredUserProgress
import id.kotoba.app.model.UserProgress
import id.kotoba.app.model.remote.NeonDatabaseClient
import id.kotoba.app.platform.SessionStorage
import id.kotoba.app.platform.getPlatformTimeMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProgressRepository(
    private val sessionStorage: SessionStorage = SessionStorage(),
    private val dbClient: NeonDatabaseClient = NeonDatabaseClient(),
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) {
    private var currentUserId: String? = null

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

    init {
        loadLocalProgress()
    }

    fun setCurrentUserId(userId: String?) {
        currentUserId = userId
        if (userId != null && !userId.startsWith("guest_")) {
            syncWithCloud(userId)
        }
    }

    private fun loadLocalProgress() {
        try {
            val stored = sessionStorage.loadProgress() ?: return
            _progressMap.value = stored.progressMap.ifEmpty {
                mapOf("lvl_1" to UserProgress("lvl_1", ProgressStatus.IN_PROGRESS, highScore = 0))
            }
            _masteredKanaIds.value = stored.masteredKanaIds

            // Update _levels state based on stored progress
            val currentList = KanaRepository.allLevels.map { level ->
                val isUnlocked = stored.unlockedLevelIds.contains(level.id) || level.id == "lvl_1"
                val isCompleted = stored.completedLevelIds.contains(level.id)
                level.copy(isUnlocked = isUnlocked, isCompleted = isCompleted)
            }
            _levels.value = currentList
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveLocalProgress() {
        val completedIds = _levels.value.filter { it.isCompleted }.map { it.id }.toSet()
        val unlockedIds = _levels.value.filter { it.isUnlocked }.map { it.id }.toSet()
        val stored = StoredUserProgress(
            progressMap = _progressMap.value,
            masteredKanaIds = _masteredKanaIds.value,
            completedLevelIds = completedIds,
            unlockedLevelIds = unlockedIds,
            lastSyncedMillis = getPlatformTimeMillis()
        )
        sessionStorage.saveProgress(stored)
    }

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
        saveLocalProgress()

        // Sync to cloud if user logged in
        val userId = currentUserId
        if (userId != null && !userId.startsWith("guest_")) {
            scope.launch {
                val level = _levels.value.find { lvl -> lvl.kanaList.any { it.id == kanaId } }
                if (level != null) {
                    val progress = _progressMap.value[level.id] ?: UserProgress(level.id, ProgressStatus.IN_PROGRESS)
                    val updatedProgress = progress.copy(masteredKanaIds = _masteredKanaIds.value)
                    dbClient.upsertProgress(userId, updatedProgress)
                }
            }
        }
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
        val updatedUserProgress = existing.copy(
            status = if (isPassed) ProgressStatus.COMPLETED else ProgressStatus.IN_PROGRESS,
            highScore = maxOf(existing.highScore, scorePercentage),
            isQuizPassed = isPassed || existing.isQuizPassed,
            masteredKanaIds = _masteredKanaIds.value
        )
        currentMap[levelId] = updatedUserProgress
        _progressMap.value = currentMap

        // Always save locally immediately (100% offline safe)
        saveLocalProgress()

        // Sync to Neon PostgreSQL asynchronously
        val userId = currentUserId
        if (userId != null && !userId.startsWith("guest_")) {
            scope.launch {
                dbClient.upsertProgress(userId, updatedUserProgress)
                if (isPassed && index != -1 && index + 1 < currentList.size) {
                    val nextLvl = currentList[index + 1]
                    val nextProgress = currentMap[nextLvl.id] ?: UserProgress(nextLvl.id, ProgressStatus.IN_PROGRESS)
                    dbClient.upsertProgress(userId, nextProgress)
                }
            }
        }

        return isPassed
    }

    fun syncWithCloud(userId: String) {
        currentUserId = userId
        scope.launch {
            try {
                val remoteList = dbClient.loadAllProgress(userId)
                if (remoteList.isEmpty()) return@launch

                val currentMap = _progressMap.value.toMutableMap()
                val currentLevels = _levels.value.toMutableList()
                val currentMastered = _masteredKanaIds.value.toMutableSet()

                remoteList.forEach { remote ->
                    val status = runCatching { ProgressStatus.valueOf(remote.status) }.getOrDefault(ProgressStatus.IN_PROGRESS)
                    val existing = currentMap[remote.levelId]

                    val merged = UserProgress(
                        levelId = remote.levelId,
                        status = if (existing?.status == ProgressStatus.COMPLETED || status == ProgressStatus.COMPLETED) {
                            ProgressStatus.COMPLETED
                        } else ProgressStatus.IN_PROGRESS,
                        highScore = maxOf(existing?.highScore ?: 0, remote.highScore),
                        isQuizPassed = (existing?.isQuizPassed == true) || remote.isQuizPassed,
                        masteredKanaIds = (existing?.masteredKanaIds ?: emptySet()) + remote.masteredKanaIds
                    )
                    currentMap[remote.levelId] = merged
                    currentMastered.addAll(remote.masteredKanaIds)

                    // Update level completion & unlocking
                    val lvlIdx = currentLevels.indexOfFirst { it.id == remote.levelId }
                    if (lvlIdx != -1) {
                        if (merged.status == ProgressStatus.COMPLETED) {
                            currentLevels[lvlIdx] = currentLevels[lvlIdx].copy(isCompleted = true, isUnlocked = true)
                            if (lvlIdx + 1 < currentLevels.size) {
                                currentLevels[lvlIdx + 1] = currentLevels[lvlIdx + 1].copy(isUnlocked = true)
                            }
                        } else {
                            currentLevels[lvlIdx] = currentLevels[lvlIdx].copy(isUnlocked = true)
                        }
                    }
                }

                _progressMap.value = currentMap
                _levels.value = currentLevels
                _masteredKanaIds.value = currentMastered

                // Save merged result to local storage
                saveLocalProgress()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun unlockUpToLevel(levelId: String) {
        val currentList = _levels.value.toMutableList()
        val targetIndex = currentList.indexOfFirst { it.id == levelId }
        if (targetIndex != -1) {
            for (i in 0..targetIndex) {
                currentList[i] = currentList[i].copy(isUnlocked = true)
            }
            _levels.value = currentList
            saveLocalProgress()
        }
    }

    fun resetProgress() {
        _levels.value = KanaRepository.allLevels
        _progressMap.value = mapOf(
            "lvl_1" to UserProgress("lvl_1", ProgressStatus.IN_PROGRESS, highScore = 0)
        )
        _masteredKanaIds.value = emptySet()
        sessionStorage.clearProgress()
    }
}
