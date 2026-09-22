package id.kotoba.app.view.navigation

import androidx.compose.runtime.compositionLocalOf
import cafe.adriel.voyager.navigator.Navigator
import id.kotoba.app.model.repository.AuthRepository
import id.kotoba.app.model.repository.GeminiRepository
import id.kotoba.app.model.repository.GoalsRepository
import id.kotoba.app.model.repository.ProgressRepository
import id.kotoba.app.model.repository.SrsRepository
import id.kotoba.app.platform.AudioTtsEngine

class AppState(
    val progressRepository: ProgressRepository,
    val authRepository: AuthRepository,
    val srsRepository: SrsRepository,
    val goalsRepository: GoalsRepository,
    val geminiRepository: GeminiRepository
)

val LocalAppState = compositionLocalOf<AppState> {
    error("LocalAppState not provided")
}

val LocalAudioTtsEngine = compositionLocalOf<AudioTtsEngine> {
    error("LocalAudioTtsEngine not provided")
}

val LocalRootNavigator = compositionLocalOf<Navigator?> {
    null
}
