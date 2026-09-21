package id.kotoba.app.controller

import id.kotoba.app.model.KanaItem
import id.kotoba.app.model.Level
import id.kotoba.app.model.repository.AuthRepository
import id.kotoba.app.model.repository.ProgressRepository
import id.kotoba.app.model.repository.SrsRepository
import id.kotoba.app.platform.AudioTtsEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LearningController(
    private val progressRepo: ProgressRepository,
    private val navController: NavigationController,
    val levelId: String,
    private val ttsEngine: AudioTtsEngine? = null,
    private val srsRepo: SrsRepository? = null,
    private val authRepo: AuthRepository? = null
) {
    val level: Level? = progressRepo.getLevelById(levelId)
    val kanaList: List<KanaItem> = level?.kanaList ?: emptyList()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _isFlipped = MutableStateFlow(false)
    val isFlipped: StateFlow<Boolean> = _isFlipped.asStateFlow()

    // Furigana toggle state
    private val _furiganaEnabled = MutableStateFlow(true)
    val furiganaEnabled: StateFlow<Boolean> = _furiganaEnabled.asStateFlow()

    // Coach mark for first learning session
    private val _showCoachMark = MutableStateFlow(levelId == "lvl_1")
    val showCoachMark: StateFlow<Boolean> = _showCoachMark.asStateFlow()

    val currentKana: KanaItem?
        get() = kanaList.getOrNull(_currentIndex.value)

    val masteredKanaIds: StateFlow<Set<String>> = progressRepo.masteredKanaIds

    fun toggleFurigana() {
        _furiganaEnabled.value = !_furiganaEnabled.value
    }

    fun dismissCoachMark() {
        _showCoachMark.value = false
    }

    fun flipCard() {
        _isFlipped.value = !_isFlipped.value
    }

    fun nextCard() {
        if (_currentIndex.value < kanaList.size - 1) {
            _currentIndex.value += 1
            _isFlipped.value = false
            playCurrentAudio()
        }
    }

    fun previousCard() {
        if (_currentIndex.value > 0) {
            _currentIndex.value -= 1
            _isFlipped.value = false
            playCurrentAudio()
        }
    }

    fun selectCardIndex(index: Int) {
        if (index in kanaList.indices) {
            _currentIndex.value = index
            _isFlipped.value = false
            playCurrentAudio()
        }
    }

    fun markMastered() {
        currentKana?.let {
            progressRepo.markKanaMastered(it.id, true)
            val userId = authRepo?.currentUser?.value?.id ?: "guest"
            srsRepo?.recordReview(userId, it.id, isCorrect = true)
            nextCard()
        }
    }

    fun markRepeat() {
        currentKana?.let {
            progressRepo.markKanaMastered(it.id, false)
            val userId = authRepo?.currentUser?.value?.id ?: "guest"
            srsRepo?.recordReview(userId, it.id, isCorrect = false)
            nextCard()
        }
    }

    fun playCurrentAudio() {
        currentKana?.let {
            ttsEngine?.speak(it.audioPrompt, "ja")
        }
    }

    fun goToQuiz() {
        navController.navigateTo(Screen.Quiz(levelId))
    }

    fun goBack() {
        navController.goBack()
    }
}
