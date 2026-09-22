@file:OptIn(ExperimentalForeignApi::class)

package id.kotoba.app.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayback
import platform.AVFAudio.AVSpeechBoundary
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance
import platform.AVFAudio.setActive

actual class AudioTtsEngine {
    companion object {
        // Keep a strong static reference to ensure AVSpeechSynthesizer is NOT deallocated during speech
        private val sharedSynthesizer: AVSpeechSynthesizer by lazy {
            AVSpeechSynthesizer()
        }
    }

    actual fun speak(text: String, lang: String) {
        if (text.isBlank()) return

        try {
            // Activate audio session in playback mode so silent switch does not mute sound
            try {
                val audioSession = AVAudioSession.sharedInstance()
                audioSession.setCategory(AVAudioSessionCategoryPlayback, error = null)
                audioSession.setActive(true, error = null)
            } catch (e: Throwable) {
                // Non-fatal if audio session is already active
            }

            // Stop any ongoing utterance
            try {
                sharedSynthesizer.stopSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)
            } catch (e: Throwable) {
                // Non-fatal
            }

            val voiceCode = if (lang == "ja") "ja-JP" else "en-US"
            val voice = AVSpeechSynthesisVoice.voiceWithLanguage(voiceCode)
                ?: AVSpeechSynthesisVoice.voiceWithLanguage("en-US")

            val utterance = AVSpeechUtterance(string = text).apply {
                this.voice = voice
                this.volume = 1.0f
                // Kecepatan 0.46f membuat artikulasi huruf/frasa bahasa Jepang jelas dan tidak terburu-buru
                this.rate = 0.46f
                this.pitchMultiplier = 1.0f
            }

            println("🔊 [AudioTts] Speaking: '$text' with voice: ${voice?.name ?: "system default"} (lang=$voiceCode)")
            sharedSynthesizer.speakUtterance(utterance)
        } catch (t: Throwable) {
            println("⚠️ [AudioTts] Failed to speak: ${t.message}")
        }
    }

    actual fun stop() {
        try {
            sharedSynthesizer.stopSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)
        } catch (t: Throwable) {
            // Silently handle
        }
    }
}

@Composable
actual fun rememberAudioTtsEngine(): AudioTtsEngine {
    return remember { AudioTtsEngine() }
}


