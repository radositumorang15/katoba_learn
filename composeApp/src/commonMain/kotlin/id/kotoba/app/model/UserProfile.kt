package id.kotoba.app.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String,
    val displayName: String,
    val email: String,
    val totalXp: Int = 0,
    val currentStreak: Int = 1,
    val coins: Int = 50,
    val avatarLetter: String = "K",
    val titleBadge: String = "Anime Otaku Explorer",
    val furiganaEnabled: Boolean = true
)
