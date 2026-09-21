package id.kotoba.app.model.repository

import id.kotoba.app.model.AuthSession
import id.kotoba.app.model.UserProfile
import id.kotoba.app.model.remote.NeonAuthClient
import id.kotoba.app.model.remote.NeonAuthResult
import id.kotoba.app.platform.SessionStorage
import id.kotoba.app.platform.getPlatformTimeMillis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepository(
    private val neonAuthClient: NeonAuthClient = NeonAuthClient(),
    private val sessionStorage: SessionStorage = SessionStorage()
) {
    private val _currentUser = MutableStateFlow<UserProfile?>(null)
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    init {
        // Automatically restore session from persistent storage on startup
        try {
            val savedSession = sessionStorage.loadSession()
            if (savedSession != null) {
                _currentUser.value = savedSession.user
                _token.value = savedSession.token
                _isLoggedIn.value = true
            }
        } catch (e: Exception) {
            // Ignore if corrupted
        }
    }

    private fun persistSession(user: UserProfile, authToken: String) {
        _currentUser.value = user
        _token.value = authToken
        _isLoggedIn.value = true
        sessionStorage.saveSession(
            AuthSession(
                user = user,
                token = authToken,
                savedAtMillis = getPlatformTimeMillis()
            )
        )
    }

    suspend fun loginWithNeon(email: String, password: String): String? {
        val result = neonAuthClient.signIn(email, password)
        return when (result) {
            is NeonAuthResult.Success -> {
                val cleanEmail = result.user.email.trim()
                val displayName = result.user.name?.takeIf { it.isNotBlank() }
                    ?: cleanEmail.substringBefore("@").replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                val initial = (displayName.firstOrNull() ?: cleanEmail.firstOrNull() ?: 'U').uppercase().toString()
                val user = UserProfile(
                    id = result.user.id,
                    displayName = displayName,
                    email = cleanEmail,
                    totalXp = 50,
                    currentStreak = 1,
                    coins = 50,
                    avatarLetter = initial,
                    titleBadge = "Anime Otaku Explorer"
                )
                persistSession(user, result.token)
                null
            }
            is NeonAuthResult.Error -> {
                result.message
            }
        }
    }

    suspend fun signUpWithNeon(email: String, password: String, name: String): String? {
        val result = neonAuthClient.signUp(email, password, name)
        return when (result) {
            is NeonAuthResult.Success -> {
                val cleanEmail = result.user.email.trim()
                val displayName = name.trim().ifBlank {
                    cleanEmail.substringBefore("@").replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                }
                val initial = (displayName.firstOrNull() ?: cleanEmail.firstOrNull() ?: 'K').uppercase().toString()
                val user = UserProfile(
                    id = result.user.id,
                    displayName = displayName,
                    email = cleanEmail,
                    totalXp = 50,
                    currentStreak = 1,
                    coins = 50,
                    avatarLetter = initial,
                    titleBadge = "Kana Novice"
                )
                persistSession(user, result.token)
                null
            }
            is NeonAuthResult.Error -> {
                result.message
            }
        }
    }

    fun login(email: String, name: String = "") {
        val cleanEmail = email.trim()
        val displayName = name.trim().ifBlank {
            cleanEmail.substringBefore("@").replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
        val initial = (displayName.firstOrNull() ?: cleanEmail.firstOrNull() ?: 'U').uppercase().toString()
        val user = UserProfile(
            id = "user_${cleanEmail.lowercase().hashCode().toString().removePrefix("-")}",
            displayName = displayName,
            email = cleanEmail,
            totalXp = 50,
            currentStreak = 1,
            coins = 50,
            avatarLetter = initial,
            titleBadge = "Anime Otaku Explorer"
        )
        persistSession(user, "demo_jwt_token")
    }

    fun continueAsGuest() {
        val guest = UserProfile(
            id = "guest_${getPlatformTimeMillis()}",
            displayName = "Guest Explorer",
            email = "guest@kotoba.local",
            totalXp = 0,
            currentStreak = 1,
            coins = 20,
            avatarLetter = "G",
            titleBadge = "Wanderer"
        )
        persistSession(guest, "guest_token")
    }

    fun logout() {
        sessionStorage.clearSession()
        _currentUser.value = null
        _token.value = null
        _isLoggedIn.value = false
    }

    fun addRewards(xp: Int, coins: Int) {
        val current = _currentUser.value ?: return
        val updated = current.copy(
            totalXp = current.totalXp + xp,
            coins = current.coins + coins
        )
        _currentUser.value = updated
        _token.value?.let { persistSession(updated, it) }
    }

    fun incrementStreak() {
        val current = _currentUser.value ?: return
        val updated = current.copy(
            currentStreak = current.currentStreak + 1
        )
        _currentUser.value = updated
        _token.value?.let { persistSession(updated, it) }
    }
}
