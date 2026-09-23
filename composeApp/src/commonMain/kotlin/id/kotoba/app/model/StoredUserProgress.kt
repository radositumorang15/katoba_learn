package id.kotoba.app.model

import kotlinx.serialization.Serializable

@Serializable
data class StoredUserProgress(
    val progressMap: Map<String, UserProgress> = emptyMap(),
    val masteredKanaIds: Set<String> = emptySet(),
    val completedLevelIds: Set<String> = emptySet(),
    val unlockedLevelIds: Set<String> = setOf("lvl_1"),
    val lastSyncedMillis: Long = 0L
)
