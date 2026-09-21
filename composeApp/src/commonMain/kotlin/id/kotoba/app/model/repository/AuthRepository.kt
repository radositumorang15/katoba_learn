package id.kotoba.app.model.repository

import id.kotoba.app.model.UserProfile
import id.kotoba.app.model.remote.NeonAuthClient
import id.kotoba.app.model.remote.NeonAuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepository(
    private val neonAuthClient: NeonAuthClient = NeonAuthClient()
) {
    private val _currentUser = MutableStateFlow<UserProfile?>(null)
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    suspend fun loginWithNeon(email: String, password: String): String? {
        val result = neonAuthClient.signIn(email, password)
        return when (result) {
            is NeonAuthResult.Success -> {
                val isRado = email.equals("radositumorang@gmail.com", ignoreCase = true)
                val user = UserProfile(
                    id = result.user.id,
                    displayName = result.user.name?.ifBlank { email.substringBefore("@") } ?: email.substringBefore("@"),
                    email = result.user.email,
                    totalXp = if (isRado) 150 else 50,
                    currentStreak = if (isRado) 3 else 1,
                    coins = if (isRado) 100 else 30,
                    avatarLetter = (result.user.name?.firstOrNull() ?: email.firstOrNull() ?: 'U').uppercase().toString(),
                    titleBadge = "Anime Otaku Explorer"
                )
                _currentUser.value = user
                _isLoggedIn.value = true
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
                val user = UserProfile(
                    id = result.user.id,
                    displayName = name.ifBlank { "Otaku Learner" },
                    email = result.user.email,
                    totalXp = 50,
                    currentStreak = 1,
                    coins = 50,
                    avatarLetter = (name.firstOrNull() ?: 'K').uppercase().toString(),
                    titleBadge = "Kana Novice"
                )
                _currentUser.value = user
                _isLoggedIn.value = true
                null
            }
            is NeonAuthResult.Error -> {
                result.message
            }
        }
    }

    fun login(email: String, name: String = "Rado Situmorang") {
        val isRado = email.equals("radositumorang@gmail.com", ignoreCase = true)
        val user = UserProfile(
            id = if (isRado) "b2f3432b-e013-4ba5-942b-2d694a9bbdb8" else "user_${email.hashCode()}",
            displayName = if (isRado) "Rado Situmorang" else name.ifBlank { "Otaku Learner" },
            email = email,
            totalXp = if (isRado) 150 else 50,
            currentStreak = if (isRado) 3 else 1,
            coins = if (isRado) 100 else 30,
            avatarLetter = if (isRado) "R" else name.firstOrNull()?.uppercase() ?: "O",
            titleBadge = "Anime Otaku Explorer"
        )
        _currentUser.value = user
        _isLoggedIn.value = true
    }

    fun continueAsGuest() {
        val guest = UserProfile(
            id = "guest_${System.currentTimeMillis()}",
            displayName = "Guest Explorer",
            email = "guest@kotoba.local",
            totalXp = 0,
            currentStreak = 1,
            coins = 20,
            avatarLetter = "G",
            titleBadge = "Wanderer"
        )
        _currentUser.value = guest
        _isLoggedIn.value = true
    }

    fun logout() {
        _currentUser.value = null
        _isLoggedIn.value = false
    }

    fun addRewards(xp: Int, coins: Int) {
        val current = _currentUser.value ?: return
        _currentUser.value = current.copy(
            totalXp = current.totalXp + xp,
            coins = current.coins + coins
        )
    }

    fun incrementStreak() {
        val current = _currentUser.value ?: return
        _currentUser.value = current.copy(
            currentStreak = current.currentStreak + 1
        )
    }
}
