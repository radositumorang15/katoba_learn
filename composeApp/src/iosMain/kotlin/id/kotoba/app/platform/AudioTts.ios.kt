package id.kotoba.app.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance

actual class AudioTtsEngine {
    private val synthesizer = AVSpeechSynthesizer()

    actual fun speak(text: String, lang: String) {
        val utterance = AVSpeechUtterance(string = text)
        val voiceCode = if (lang == "ja") "ja-JP" else "en-US"
        utterance.voice = AVSpeechSynthesisVoice.voiceWithLanguage(voiceCode)
        synthesizer.speakUtterance(utterance)
    }

    actual fun stop() {
        synthesizer.stopSpeakingAtBoundary(platform.AVFAudio.AVSpeechBoundary.AVSpeechBoundaryImmediate)
    }
}

@Composable
actual fun rememberAudioTtsEngine(): AudioTtsEngine {
    return remember { AudioTtsEngine() }
}
