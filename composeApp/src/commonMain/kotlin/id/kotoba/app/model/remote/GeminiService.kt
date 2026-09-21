package id.kotoba.app.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class AiChatMessage(
    val id: String,
    val sender: String, // "user" or "sensei"
    val text: String,
    val timestampMillis: Long = 0L,
    val romaji: String? = null,
    val indonesianMeaning: String? = null
)

sealed class GeminiResult {
    data class Success(val text: String) : GeminiResult()
    data class Error(val message: String, val isOffline: Boolean = false) : GeminiResult()
}

expect class GeminiApiClient() {
    suspend fun generateContent(prompt: String, systemInstruction: String? = null): GeminiResult
}
