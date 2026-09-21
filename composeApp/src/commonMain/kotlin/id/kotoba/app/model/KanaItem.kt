package id.kotoba.app.model

import kotlinx.serialization.Serializable

@Serializable
enum class KanaType {
    HIRAGANA_SEION,
    HIRAGANA_DAKUON,
    YOUON_SOKUON,
    ANIME_VOCAB,
    KATAKANA,
    PARTICLE,
    KANJI,
    CONVERSATION
}

@Serializable
data class KanaItem(
    val id: String,
    val character: String,
    val romaji: String,
    val type: KanaType,
    val animeContext: String,
    val meaning: String,
    val audioPrompt: String = character,
    val mnemonic: String = "",
    val furigana: String? = null,
    val isMastered: Boolean = false
)
