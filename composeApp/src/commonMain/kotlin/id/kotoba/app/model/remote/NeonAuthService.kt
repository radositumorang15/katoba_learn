package id.kotoba.app.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class NeonUser(
    val id: String,
    val name: String? = null,
    val email: String,
    val role: String? = null
)

sealed class NeonAuthResult {
    data class Success(val user: NeonUser, val token: String) : NeonAuthResult()
    data class Error(val message: String) : NeonAuthResult()
}

expect class NeonAuthClient() {
    suspend fun signIn(email: String, password: String): NeonAuthResult
    suspend fun signUp(email: String, password: String, name: String): NeonAuthResult
}
