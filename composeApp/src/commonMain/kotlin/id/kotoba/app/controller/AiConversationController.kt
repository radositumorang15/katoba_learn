package id.kotoba.app.controller

import id.kotoba.app.model.remote.AiChatMessage
import id.kotoba.app.model.repository.GeminiRepository
import id.kotoba.app.model.repository.SrsRepository
import id.kotoba.app.platform.AudioTtsEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AiConversationController(
    private val geminiRepo: GeminiRepository,
    private val srsRepo: SrsRepository,
    private val navController: NavigationController,
    private val ttsEngine: AudioTtsEngine,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
) {
    private val initialWelcome = AiChatMessage(
        id = "welcome_1",
        sender = "sensei",
        text = "Konnichiwa! (こんにちは！)\nSaya Katoba Sensei ⛩️. Selamat datang di sesi Ngobrol Bebas! Ayo coba kirim sapaan atau ceritakan harimu dalam bahasa Jepang sederhana."
    )

    private val _messages = MutableStateFlow<List<AiChatMessage>>(listOf(initialWelcome))
    val messages: StateFlow<List<AiChatMessage>> = _messages.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val quickPrompts = listOf(
        "こんにちは！はじめまして",
        "今日はいい天気ですね",
        "おすすめのアニメは何ですか？",
        "日本語を勉強しています！"
    )

    fun updateInputText(newText: String) {
        _inputText.value = newText
    }

    fun sendQuickPrompt(prompt: String) {
        _inputText.value = prompt
        sendMessage()
    }

    fun sendMessage() {
        val text = _inputText.value.trim()
        if (text.isBlank() || _isLoading.value) return

        val userMessage = AiChatMessage(
            id = "user_${text.hashCode()}_${_messages.value.size}",
            sender = "user",
            text = text
        )

        _messages.value = _messages.value + userMessage
        _inputText.value = ""
        _isLoading.value = true
        _errorMessage.value = null

        scope.launch {
            // Get user's mastered words for level-adaptive prompting
            val masteredWords = srsRepo.itemProgressMap.value
                .filter { it.value.status == id.kotoba.app.model.ItemStatus.MASTERED || it.value.status == id.kotoba.app.model.ItemStatus.REVIEWING }
                .keys
                .toList()

            val result = geminiRepo.chatWithSensei(
                history = _messages.value,
                userMessage = text,
                masteredWords = masteredWords
            )

            result.onSuccess { senseiMsg ->
                _messages.value = _messages.value + senseiMsg
                _isLoading.value = false
            }.onFailure { err ->
                _isLoading.value = false
                _errorMessage.value = err.message ?: "Gagal terhubung ke Gemini Sensei"
            }
        }
    }

    fun playAudio(messageText: String) {
        // Extract Japanese lines to play via TTS
        val lines = messageText.lines().map { it.trim() }.filter { it.isNotBlank() }
        val japaneseCandidate = lines.firstOrNull { line ->
            // Check if line contains Japanese characters (Hiragana, Katakana, Kanji)
            line.any { it in '\u3040'..'\u30ff' || it in '\u4e00'..'\u9faf' }
        } ?: lines.firstOrNull() ?: messageText

        runCatching { ttsEngine.speak(japaneseCandidate) }
    }

    fun dismissError() {
        _errorMessage.value = null
    }

    fun goBack() {
        navController.goBack()
    }
}
