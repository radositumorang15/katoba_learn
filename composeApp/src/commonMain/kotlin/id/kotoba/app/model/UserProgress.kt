package id.kotoba.app.model

import kotlinx.serialization.Serializable

@Serializable
enum class ProgressStatus {
    LOCKED,
    IN_PROGRESS,
    COMPLETED
}

@Serializable
data class UserProgress(
    val levelId: String,
    val status: ProgressStatus,
    val highScore: Int = 0,
    val isQuizPassed: Boolean = false,
    val masteredKanaIds: Set<String> = emptySet()
)
