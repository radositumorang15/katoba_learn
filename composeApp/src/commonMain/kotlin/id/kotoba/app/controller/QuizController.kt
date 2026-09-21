package id.kotoba.app.controller

import id.kotoba.app.model.Level
import id.kotoba.app.model.QuizQuestion
import id.kotoba.app.model.repository.AuthRepository
import id.kotoba.app.model.repository.KanaRepository
import id.kotoba.app.model.repository.ProgressRepository
import id.kotoba.app.platform.AudioTtsEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class QuizResult(
    val totalQuestions: Int,
    val correctCount: Int,
    val scorePercentage: Int,
    val isPassed: Boolean,
    val earnedXp: Int,
    val earnedCoins: Int
)

class QuizController(
    val levelId: String,
    private val progressRepo: ProgressRepository,
    private val authRepo: AuthRepository,
    private val navController: NavigationController,
    private val ttsEngine: AudioTtsEngine? = null
) {
    val level: Level? = progressRepo.getLevelById(levelId)
    val questions: List<QuizQuestion> = KanaRepository.quizBank[levelId]
        ?: KanaRepository.quizBank["lvl_1"] ?: emptyList()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _selectedOptionIndex = MutableStateFlow<Int?>(null)
    val selectedOptionIndex: StateFlow<Int?> = _selectedOptionIndex.asStateFlow()

    private val _isAnswerSubmitted = MutableStateFlow(false)
    val isAnswerSubmitted: StateFlow<Boolean> = _isAnswerSubmitted.asStateFlow()

    private val _correctCount = MutableStateFlow(0)
    val correctCount: StateFlow<Int> = _correctCount.asStateFlow()

    private val _isQuizCompleted = MutableStateFlow(false)
    val isQuizCompleted: StateFlow<Boolean> = _isQuizCompleted.asStateFlow()

    private val _quizResult = MutableStateFlow<QuizResult?>(null)
    val quizResult: StateFlow<QuizResult?> = _quizResult.asStateFlow()

    private val _showConfetti = MutableStateFlow(false)
    val showConfetti: StateFlow<Boolean> = _showConfetti.asStateFlow()

    val currentQuestion: QuizQuestion?
        get() = questions.getOrNull(_currentIndex.value)

    fun playQuestionAudio() {
        currentQuestion?.let {
            ttsEngine?.speak(it.audioPrompt, "ja")
        }
    }

    fun selectOption(index: Int) {
        if (!_isAnswerSubmitted.value) {
            _selectedOptionIndex.value = index
        }
    }

    fun submitAnswer() {
        val selected = _selectedOptionIndex.value ?: return
        val question = currentQuestion ?: return

        _isAnswerSubmitted.value = true
        if (selected == question.correctOptionIndex) {
            _correctCount.value += 1
        }
    }

    fun nextQuestion() {
        if (_currentIndex.value < questions.size - 1) {
            _currentIndex.value += 1
            _selectedOptionIndex.value = null
            _isAnswerSubmitted.value = false
            playQuestionAudio()
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        val total = questions.size
        val correct = _correctCount.value
        val percentage = if (total > 0) (correct * 100) / total else 0
        val isPassed = percentage >= 80

        val earnedXp = if (isPassed) (level?.xpReward ?: 100) else 25
        val earnedCoins = if (isPassed) 30 else 5

        if (isPassed) {
            progressRepo.completeQuiz(levelId, percentage, earnedXp)
            authRepo.addRewards(earnedXp, earnedCoins)
            authRepo.incrementStreak()
            _showConfetti.value = true
        }

        _quizResult.value = QuizResult(
            totalQuestions = total,
            correctCount = correct,
            scorePercentage = percentage,
            isPassed = isPassed,
            earnedXp = earnedXp,
            earnedCoins = earnedCoins
        )
        _isQuizCompleted.value = true
    }

    fun restartQuiz() {
        _currentIndex.value = 0
        _selectedOptionIndex.value = null
        _isAnswerSubmitted.value = false
        _correctCount.value = 0
        _isQuizCompleted.value = false
        _quizResult.value = null
        _showConfetti.value = false
        playQuestionAudio()
    }

    fun continueToNextLevel() {
        navController.navigateTo(Screen.Roadmap)
    }

    fun goBack() {
        navController.goBack()
    }
}
