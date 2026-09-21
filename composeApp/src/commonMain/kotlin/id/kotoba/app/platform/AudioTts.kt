package id.kotoba.app.platform

import androidx.compose.runtime.Composable

expect class AudioTtsEngine {
    fun speak(text: String, lang: String = "ja")
    fun stop()
}

@Composable
expect fun rememberAudioTtsEngine(): AudioTtsEngine
