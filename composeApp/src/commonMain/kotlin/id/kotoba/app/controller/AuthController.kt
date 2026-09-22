package id.kotoba.app.controller

import id.kotoba.app.model.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import id.kotoba.app.model.repository.GoalsRepository
import kotlinx.coroutines.launch

enum class AuthMode {
    SIGN_IN,
    SIGN_UP
}

class AuthController(
    private val authRepo: AuthRepository,
    private val navController: NavigationController,
    private val goalsRepo: GoalsRepository? = null
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _authMode = MutableStateFlow(AuthMode.SIGN_IN)
    val authMode: StateFlow<AuthMode> = _authMode.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _displayName = MutableStateFlow("")
    val displayName: StateFlow<String> = _displayName.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isAgeAndPrivacyAccepted = MutableStateFlow(false)
    val isAgeAndPrivacyAccepted: StateFlow<Boolean> = _isAgeAndPrivacyAccepted.asStateFlow()

    fun setAuthMode(mode: AuthMode) {
        _authMode.value = mode
        _errorMessage.value = null
    }

    fun toggleAgeAndPrivacyAccepted() {
        _isAgeAndPrivacyAccepted.value = !_isAgeAndPrivacyAccepted.value
    }

    fun onEmailChange(value: String) {
        _email.value = value
    }

    fun onDisplayNameChange(value: String) {
        _displayName.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun submit() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _errorMessage.value = "Email dan password tidak boleh kosong!"
            return
        }

        if (_authMode.value == AuthMode.SIGN_UP && !_isAgeAndPrivacyAccepted.value) {
            _errorMessage.value = "Konfirmasi usia 13+ tahun dan persetujuan privasi wajib dicentang!"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        scope.launch {
            try {
                val isSignUp = _authMode.value == AuthMode.SIGN_UP
                val error = if (!isSignUp) {
                    authRepo.loginWithNeon(_email.value.trim(), _password.value)
                } else {
                    authRepo.signUpWithNeon(_email.value.trim(), _password.value, _displayName.value.trim())
                }

                _isLoading.value = false
                if (error == null) {
                    if (isSignUp) {
                        // Route to Goal Quiz directly after Sign Up (PRD Fase 1.5)
                        navController.navigateTo(Screen.GoalQuiz, clearStack = true)
                    } else {
                        // Existing user on relog: mark default completed if not set and go straight to Home
                        val user = authRepo.currentUser.value
                        if (user != null) {
                            goalsRepo?.markDefaultCompleted(user.id)
                        }
                        navController.navigateTo(Screen.Home, clearStack = true)
                    }
                } else {
                    _errorMessage.value = error
                }
            } catch (t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message ?: "Terjadi kesalahan pada sistem autentikasi."
            }
        }
    }

    fun continueAsGuest() {
        authRepo.continueAsGuest()
        if (goalsRepo?.hasCompletedGoalQuiz() == true) {
            navController.navigateTo(Screen.Home, clearStack = true)
        } else {
            navController.navigateTo(Screen.GoalQuiz, clearStack = true)
        }
    }
}
