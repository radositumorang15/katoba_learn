package id.kotoba.app.controller

import id.kotoba.app.model.KanaItem
import id.kotoba.app.model.repository.AuthRepository
import id.kotoba.app.model.repository.KanaRepository
import id.kotoba.app.model.repository.SrsRepository
import id.kotoba.app.platform.AudioTtsEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReviewSessionController(
    private val srsRepo: SrsRepository,
    private val authRepo: AuthRepository,
    private val navController: NavigationController,
    private val ttsEngine: AudioTtsEngine
) {
    private val _reviewItems = MutableStateFlow<List<KanaItem>>(emptyList())
    val reviewItems: StateFlow<List<KanaItem>> = _reviewItems.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _isFlipped = MutableStateFlow(false)
    val isFlipped: StateFlow<Boolean> = _isFlipped.asStateFlow()

    private val _correctCount = MutableStateFlow(0)
    val correctCount: StateFlow<Int> = _correctCount.asStateFlow()

    private val _repeatCount = MutableStateFlow(0)
    val repeatCount: StateFlow<Int> = _repeatCount.asStateFlow()

    private val _isCompleted = MutableStateFlow(false)
    val isCompleted: StateFlow<Boolean> = _isCompleted.asStateFlow()

    private val _earnedXp = MutableStateFlow(0)
    val earnedXp: StateFlow<Int> = _earnedXp.asStateFlow()

    init {
        // Collect all items across levels
        val allItems = mutableListOf<KanaItem>()
        KanaRepository.allLevels.forEach { lvl ->
            allItems.addAll(lvl.kanaList)
        }
        val due = srsRepo.getDueItems(allItems)
        _reviewItems.value = due
        if (due.isNotEmpty()) {
            // Play audio prompt for first card
            ttsEngine.speak(due.first().audioPrompt)
        }
    }

    val currentItem: KanaItem?
        get() {
            val list = _reviewItems.value
            val idx = _currentIndex.value
            return if (idx in list.indices) list[idx] else null
        }

    fun flipCard() {
        _isFlipped.value = !_isFlipped.value
    }

    fun playAudio() {
        currentItem?.let { item ->
            ttsEngine.speak(item.audioPrompt)
        }
    }

    fun answerCorrect() {
        val item = currentItem ?: return
        val user = authRepo.currentUser.value
        val userId = user?.id ?: "guest"

        srsRepo.recordReview(userId, item.id, isCorrect = true)
        _correctCount.value += 1
        advanceNext()
    }

    fun answerRepeat() {
        val item = currentItem ?: return
        val user = authRepo.currentUser.value
        val userId = user?.id ?: "guest"

        srsRepo.recordReview(userId, item.id, isCorrect = false)
        _repeatCount.value += 1
        advanceNext()
    }

    private fun advanceNext() {
        _isFlipped.value = false
        val nextIdx = _currentIndex.value + 1
        if (nextIdx < _reviewItems.value.size) {
            _currentIndex.value = nextIdx
            // Play next audio
            _reviewItems.value.getOrNull(nextIdx)?.let {
                ttsEngine.speak(it.audioPrompt)
            }
        } else {
            // Completed session!
            val xp = _correctCount.value * 20 + _repeatCount.value * 5
            _earnedXp.value = xp
            _isCompleted.value = true
            // Award XP to profile
            authRepo.addRewards(xp = xp, coins = maxOf(5, xp / 2))
        }
    }

    fun exitSession() {
        navController.navigateTo(Screen.Home, clearStack = true)
    }
}
