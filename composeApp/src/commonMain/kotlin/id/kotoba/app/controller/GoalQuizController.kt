package id.kotoba.app.controller

import id.kotoba.app.model.CurrentKnowledge
import id.kotoba.app.model.PrimaryGoal
import id.kotoba.app.model.repository.AuthRepository
import id.kotoba.app.model.repository.GoalsRepository
import id.kotoba.app.model.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GoalQuizController(
    private val goalsRepo: GoalsRepository,
    private val progressRepo: ProgressRepository,
    private val authRepo: AuthRepository,
    private val navController: NavigationController
) {
    // Current question index: 0 = Goal, 1 = Knowledge, 2 = Daily Target, 3 = Summary/Placement
    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _selectedGoal = MutableStateFlow(PrimaryGoal.ANIME_COMPREHENSION)
    val selectedGoal: StateFlow<PrimaryGoal> = _selectedGoal.asStateFlow()

    private val _selectedKnowledge = MutableStateFlow(CurrentKnowledge.ZERO)
    val selectedKnowledge: StateFlow<CurrentKnowledge> = _selectedKnowledge.asStateFlow()

    private val _selectedDailyMinutes = MutableStateFlow(15)
    val selectedDailyMinutes: StateFlow<Int> = _selectedDailyMinutes.asStateFlow()

    fun selectGoal(goal: PrimaryGoal) {
        _selectedGoal.value = goal
    }

    fun selectKnowledge(knowledge: CurrentKnowledge) {
        _selectedKnowledge.value = knowledge
    }

    fun selectDailyMinutes(minutes: Int) {
        _selectedDailyMinutes.value = minutes
    }

    fun nextStep() {
        if (_currentStep.value < 3) {
            _currentStep.value += 1
        }
    }

    fun prevStep() {
        if (_currentStep.value > 0) {
            _currentStep.value -= 1
        } else {
            navController.goBack()
        }
    }

    fun finishQuiz() {
        val user = authRepo.currentUser.value
        val userId = user?.id ?: "guest"
        val startingLevel = _selectedKnowledge.value.suggestedLevelId

        // Save goals
        goalsRepo.saveGoal(
            userId = userId,
            primaryGoal = _selectedGoal.value,
            currentKnowledge = _selectedKnowledge.value,
            dailyTargetMinutes = _selectedDailyMinutes.value
        )

        // Unlock levels up to starting level
        progressRepo.unlockUpToLevel(startingLevel)

        // Navigate to Home
        navController.navigateTo(Screen.Home, clearStack = true)
    }
}
