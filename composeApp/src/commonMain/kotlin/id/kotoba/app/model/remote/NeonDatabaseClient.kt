package id.kotoba.app.model.remote

import id.kotoba.app.model.QuizQuestion
import id.kotoba.app.model.UserProgress
import kotlinx.serialization.Serializable

@Serializable
data class RemoteUserProgress(
    val levelId: String,
    val status: String,
    val highScore: Int = 0,
    val isQuizPassed: Boolean = false,
    val masteredKanaIds: List<String> = emptyList()
)

expect class NeonDatabaseClient() {
    suspend fun upsertProgress(
        userId: String,
        progress: UserProgress
    ): Boolean

    suspend fun loadAllProgress(
        userId: String
    ): List<RemoteUserProgress>

    suspend fun updateUserProfile(
        userId: String,
        totalXp: Int,
        coins: Int,
        streak: Int
    ): Boolean

    suspend fun loadQuizQuestions(
        levelId: String
    ): List<QuizQuestion>
}
