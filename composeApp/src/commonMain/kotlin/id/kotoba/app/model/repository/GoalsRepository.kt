package id.kotoba.app.model.repository

import id.kotoba.app.model.CurrentKnowledge
import id.kotoba.app.model.PrimaryGoal
import id.kotoba.app.model.UserGoal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GoalsRepository {

    private val _userGoal = MutableStateFlow<UserGoal?>(null)
    val userGoal: StateFlow<UserGoal?> = _userGoal.asStateFlow()

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
        return goal
    }

    fun hasCompletedGoalQuiz(): Boolean {
        return _userGoal.value?.isCompleted == true
    }
}
