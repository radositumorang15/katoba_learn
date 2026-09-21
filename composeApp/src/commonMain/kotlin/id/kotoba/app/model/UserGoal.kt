package id.kotoba.app.model

import kotlinx.serialization.Serializable

@Serializable
enum class PrimaryGoal(val title: String, val description: String) {
    ANIME_COMPREHENSION(
        title = "Ngerti Anime & Meme",
        description = "Paham dialog karakter dan meme pop-culture Jepang tanpa subtitle"
    ),
    JLPT_N5(
        title = "Lulus JLPT N5",
        description = "Kuasai Kanji, kosakata, dan tata bahasa terstruktur untuk sertifikasi"
    ),
    CONVERSATION(
        title = "Ngobrol Santai & Traveling",
        description = "Lancar percakapan dasar sehari-hari di konbini, jalan-jalan, dan perkenalan"
    )
}

@Serializable
enum class CurrentKnowledge(val title: String, val description: String, val suggestedLevelId: String) {
    ZERO(
        title = "Nol Besar",
        description = "Belum kenal huruf Jepang sama sekali, mulai dari vokal dasar (Level 1)",
        suggestedLevelId = "lvl_1"
    ),
    SOME_KANA(
        title = "Hafal Sebagian Hiragana",
        description = "Sudah tahu vokal & konsonan awal, siap masuk modifikasi suara (Level 2)",
        suggestedLevelId = "lvl_2"
    ),
    READY_FOR_KANJI(
        title = "Sudah Hafal Kana Lengkap",
        description = "Sudah lancar Hiragana & Katakana, langsung tancap gas ke Kosakata & Kanji (Level 4)",
        suggestedLevelId = "lvl_4"
    )
}

@Serializable
data class UserGoal(
    val id: String = "goal_default",
    val userId: String = "",
    val primaryGoal: PrimaryGoal = PrimaryGoal.ANIME_COMPREHENSION,
    val currentKnowledge: CurrentKnowledge = CurrentKnowledge.ZERO,
    val startingLevelId: String = "lvl_1",
    val dailyTargetMinutes: Int = 15,
    val isCompleted: Boolean = false
)
