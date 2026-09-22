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

import id.kotoba.app.model.repository.DynamicSentenceExample
import id.kotoba.app.model.repository.GeminiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class LearningController(
    private val progressRepo: ProgressRepository,
    private val navController: NavigationController,
    val levelId: String,
    private val ttsEngine: AudioTtsEngine? = null,
    private val srsRepo: SrsRepository? = null,
    private val authRepo: AuthRepository? = null,
    private val geminiRepo: GeminiRepository = GeminiRepository(),
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
) {
    val level: Level? = progressRepo.getLevelById(levelId)
    val kanaList: List<KanaItem> = level?.kanaList ?: emptyList()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _isFlipped = MutableStateFlow(false)
    val isFlipped: StateFlow<Boolean> = _isFlipped.asStateFlow()

    // Dynamic Gemini AI example state
    private val _currentAiExample = MutableStateFlow<DynamicSentenceExample?>(null)
    val currentAiExample: StateFlow<DynamicSentenceExample?> = _currentAiExample.asStateFlow()

    private val _isLoadingAiExample = MutableStateFlow(false)
    val isLoadingAiExample: StateFlow<Boolean> = _isLoadingAiExample.asStateFlow()

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
            _currentAiExample.value = null
            playCurrentAudio()
        }
    }

    fun previousCard() {
        if (_currentIndex.value > 0) {
            _currentIndex.value -= 1
            _isFlipped.value = false
            _currentAiExample.value = null
            playCurrentAudio()
        }
    }

    fun selectCardIndex(index: Int) {
        if (index in kanaList.indices) {
            _currentIndex.value = index
            _isFlipped.value = false
            _currentAiExample.value = null
            playCurrentAudio()
        }
    }

    fun generateAiExample() {
        val kana = currentKana ?: return
        if (_isLoadingAiExample.value) return

        _isLoadingAiExample.value = true
        scope.launch {
            val result = geminiRepo.generateExampleSentence(
                word = kana.character,
                meaning = kana.meaning
            )
            result.onSuccess { example ->
                _currentAiExample.value = example
                _isLoadingAiExample.value = false
            }.onFailure {
                _isLoadingAiExample.value = false
            }
        }
    }

    fun playAiExampleAudio(text: String) {
        runCatching { ttsEngine?.speak(text, "ja") }
    }

    fun goToAiConversation() {
        navController.navigateTo(Screen.AiConversation)
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
            runCatching { ttsEngine?.speak(it.audioPrompt, "ja") }
        }
    }

    fun goToQuiz() {
        navController.navigateTo(Screen.Quiz(levelId))
    }

    fun goBack() {
        navController.goBack()
    }
}
