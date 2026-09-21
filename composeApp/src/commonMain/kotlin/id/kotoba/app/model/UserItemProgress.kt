package id.kotoba.app.model

import id.kotoba.app.platform.getPlatformTimeMillis
import kotlinx.serialization.Serializable

@Serializable
enum class ItemStatus {
    NEW,
    LEARNING,
    REVIEWING,
    MASTERED
}

@Serializable
data class UserItemProgress(
    val id: String = "",
    val userId: String = "",
    val itemId: String,
    val status: ItemStatus = ItemStatus.NEW,
    val easeFactor: Float = 2.5f,
    val intervalDays: Int = 0,
    val nextReviewDateMillis: Long = 0L,
    val lastReviewedAtMillis: Long = 0L,
    val correctStreak: Int = 0
) {
    val isDue: Boolean
        get() {
            if (status == ItemStatus.NEW) return false
            val now = getPlatformTimeMillis()
            return nextReviewDateMillis <= now
        }
}
