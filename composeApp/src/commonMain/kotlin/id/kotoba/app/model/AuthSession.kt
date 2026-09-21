package id.kotoba.app.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthSession(
    val user: UserProfile,
    val token: String,
    val savedAtMillis: Long = 0L
)
