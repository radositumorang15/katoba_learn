package id.kotoba.app.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    val id: String,
    val levelId: String,
    val questionText: String,
    val audioPrompt: String,
    val visualCharacter: String? = null,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String,
    val animeContext: String
)
