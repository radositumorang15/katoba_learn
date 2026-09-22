package id.kotoba.app.model.repository

import id.kotoba.app.model.CurrentKnowledge
import id.kotoba.app.model.PrimaryGoal
import id.kotoba.app.model.UserGoal
import id.kotoba.app.platform.SessionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GoalsRepository(
    private val sessionStorage: SessionStorage = SessionStorage()
) {

    private val _userGoal = MutableStateFlow<UserGoal?>(null)
    val userGoal: StateFlow<UserGoal?> = _userGoal.asStateFlow()

    init {
        try {
            val saved = sessionStorage.loadGoal()
            if (saved != null) {
                _userGoal.value = saved
            }
        } catch (_: Throwable) {
            // Ignore load errors
        }
    }

    fun saveGoal(
        userId: String,
        primaryGoal: PrimaryGoal,
        currentKnowledge: CurrentKnowledge,
        dailyTargetMinutes: Int
    ): UserGoal {
        val startingLevelId = currentKnowledge.suggestedLevelId
        val goal = UserGoal(
            id = "goal_${userId.ifEmpty { "default" }}",
            userId = userId,
            primaryGoal = primaryGoal,
            currentKnowledge = currentKnowledge,
            startingLevelId = startingLevelId,
            dailyTargetMinutes = dailyTargetMinutes,
            isCompleted = true
        )
        _userGoal.value = goal
        try {
            sessionStorage.saveGoal(goal)
        } catch (_: Throwable) {
            // Ignore save errors
        }
        return goal
    }

    fun markDefaultCompleted(userId: String) {
        if (_userGoal.value == null || !_userGoal.value!!.isCompleted) {
            saveGoal(
                userId = userId,
                primaryGoal = PrimaryGoal.ANIME_COMPREHENSION,
                currentKnowledge = CurrentKnowledge.ZERO,
                dailyTargetMinutes = 15
            )
        }
    }

    fun hasCompletedGoalQuiz(): Boolean {
        if (_userGoal.value?.isCompleted == true) return true
        val saved = try { sessionStorage.loadGoal() } catch (_: Throwable) { null }
        if (saved?.isCompleted == true) {
            _userGoal.value = saved
            return true
        }
        return false
    }
}

