package id.kotoba.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Level(
    val id: String,
    val levelNumber: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val category: String,
    val totalLessons: Int,
    val xpReward: Int,
    val isUnlocked: Boolean = false,
    val isCompleted: Boolean = false,
    val kanaList: List<KanaItem> = emptyList()
)
