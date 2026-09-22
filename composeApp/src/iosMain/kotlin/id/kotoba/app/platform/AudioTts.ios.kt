package id.kotoba.app.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance

actual class AudioTtsEngine {
    private val synthesizer by lazy {
        try {
            AVSpeechSynthesizer()
        } catch (t: Throwable) {
            null
        }
    }

    actual fun speak(text: String, lang: String) {
        try {
            val utterance = AVSpeechUtterance(string = text)
            val voiceCode = if (lang == "ja") "ja-JP" else "en-US"
            utterance.voice = AVSpeechSynthesisVoice.voiceWithLanguage(voiceCode)
            synthesizer?.speakUtterance(utterance)
        } catch (t: Throwable) {
            // Silently handle any audio engine issue
        }
    }

    actual fun stop() {
        try {
            synthesizer?.stopSpeakingAtBoundary(platform.AVFAudio.AVSpeechBoundary.AVSpeechBoundaryImmediate)
        } catch (t: Throwable) {
            // Silently handle any audio engine issue
        }
    }
}

@Composable
actual fun rememberAudioTtsEngine(): AudioTtsEngine {
    return remember { AudioTtsEngine() }
}
