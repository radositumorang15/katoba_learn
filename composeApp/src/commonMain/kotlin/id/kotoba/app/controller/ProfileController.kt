package id.kotoba.app.controller

import id.kotoba.app.model.UserProfile
import id.kotoba.app.model.repository.AuthRepository
import id.kotoba.app.model.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileController(
    private val authRepo: AuthRepository,
    private val progressRepo: ProgressRepository,
    private val navController: NavigationController
) {
    val currentUser: StateFlow<UserProfile?> = authRepo.currentUser
    val masteredCount: StateFlow<Set<String>> = progressRepo.masteredKanaIds

    private val _isAudioSpeedNormal = MutableStateFlow(true)
    val isAudioSpeedNormal: StateFlow<Boolean> = _isAudioSpeedNormal.asStateFlow()

    fun toggleAudioSpeed() {
        _isAudioSpeedNormal.value = !_isAudioSpeedNormal.value
    }

    fun resetProgress() {
        progressRepo.resetProgress()
    }

    fun logout() {
        authRepo.logout()
        navController.navigateTo(Screen.Auth, clearStack = true)
    }

    fun goBack() {
        navController.goBack()
    }
}
