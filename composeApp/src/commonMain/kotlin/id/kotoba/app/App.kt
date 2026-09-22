package id.kotoba.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import id.kotoba.app.model.repository.AuthRepository
import id.kotoba.app.model.repository.GeminiRepository
import id.kotoba.app.model.repository.GoalsRepository
import id.kotoba.app.model.repository.ProgressRepository
import id.kotoba.app.model.repository.SrsRepository
import id.kotoba.app.platform.rememberAudioTtsEngine
import id.kotoba.app.view.navigation.AppState
import id.kotoba.app.view.navigation.LocalAppState
import id.kotoba.app.view.navigation.LocalAudioTtsEngine
import id.kotoba.app.view.navigation.LocalRootNavigator
import id.kotoba.app.view.navigation.SplashVoyagerScreen
import id.kotoba.app.view.theme.KotobaTheme

@Composable
fun App() {
    KotobaTheme {
        // Compose renders the initial frame first.
        // Repository initialization runs in LaunchedEffect so that app startup is instant.
        var appState by remember { mutableStateOf<AppState?>(null) }
        var initError by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(Unit) {
            runCatching {
                AppState(
                    progressRepository = ProgressRepository(),
                    authRepository = AuthRepository(),
                    srsRepository = SrsRepository(),
                    goalsRepository = GoalsRepository(),
                    geminiRepository = GeminiRepository()
                )
            }.onSuccess { state ->
                appState = state
            }.onFailure { e ->
                println("💥 [App] Repository init failed: ${e.message}\n${e.stackTraceToString()}")
                initError = e.message ?: e::class.simpleName ?: "Unknown error"
            }
        }

        // Show error screen if init failed (helps debug without crash)
        val error = initError
        if (error != null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "⚠️ Startup Error:\n$error\n\nPlease reinstall the app.",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }
            return@KotobaTheme
        }

        // Native white background while repository connections open (usually < 20ms)
        val state = appState
        if (state == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            )
            return@KotobaTheme
        }

        // Audio TTS Engine
        val ttsEngine = rememberAudioTtsEngine()
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        // Repositories ready – mount Voyager Root Navigator
        CompositionLocalProvider(
            LocalAppState provides state,
            LocalAudioTtsEngine provides ttsEngine
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        focusManager.clearFocus(force = true)
                        keyboardController?.hide()
                    }
            ) {
                Navigator(SplashVoyagerScreen) { rootNavigator ->
                    CompositionLocalProvider(LocalRootNavigator provides rootNavigator) {
                        SlideTransition(rootNavigator)
                    }
                }
            }
        }
    }
}
