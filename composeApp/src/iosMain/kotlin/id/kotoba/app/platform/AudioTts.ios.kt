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
import platform.Foundation.NSLog

actual class AudioTtsEngine {
    companion object {
        // Keep a strong static reference to ensure AVSpeechSynthesizer is NOT deallocated during speech
        private val sharedSynthesizer: AVSpeechSynthesizer by lazy {
            val synth = AVSpeechSynthesizer()
            NSLog("🔊 [AudioTts] AVSpeechSynthesizer initialized")
            synth
        }
    }

    actual fun speak(text: String, lang: String) {
        if (text.isBlank()) return

        try {
            // Activate audio session in playback mode so silent switch does not mute sound
            val audioSession = AVAudioSession.sharedInstance()
            audioSession.setCategory(AVAudioSessionCategoryPlayback, error = null)
            audioSession.setActive(true, error = null)

            // Stop any ongoing utterance
            sharedSynthesizer.stopSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)

            val voiceCode = if (lang == "ja") "ja-JP" else "en-US"
            val matchingVoices = AVSpeechSynthesisVoice.speechVoices()
                .filterIsInstance<AVSpeechSynthesisVoice>()
                .filter { it.language.startsWith(if (lang == "ja") "ja" else "en", ignoreCase = true) }

            // Prioritize highest quality voice installed (Premium > Enhanced > Default)
            val voice = matchingVoices.maxByOrNull { it.quality }
                ?: AVSpeechSynthesisVoice.voiceWithLanguage(voiceCode)
                ?: AVSpeechSynthesisVoice.speechVoices().firstOrNull() as? AVSpeechSynthesisVoice

            val utterance = AVSpeechUtterance(string = text).apply {
                this.voice = voice
                this.volume = 1.0f
                // Kecepatan 0.44f membuat pengucapan huruf Kana satu per satu (seperti 'あ', 'い')
                // terdengar sangat jelas, artikulatif, dan tidak terburu-buru/terpotong.
                this.rate = 0.44f
                this.pitchMultiplier = 1.0f
                // Memberi jeda 50ms agar DAC hardware speaker iPhone siap dan tidak memotong huruf depan
                this.preUtteranceDelay = 0.05
            }

            if (voice != null) {
                NSLog("🔊 [AudioTts] Speaking: '%@' with voice: %@ (quality=%ld, lang=%@)", 
                    text, voice.name, voice.quality.toLong(), voice.language)
            } else {
                NSLog("⚠️ [AudioTts] Speaking: '%@' with system default voice (no %@ voice found)", text, voiceCode)
            }

            sharedSynthesizer.speakUtterance(utterance)
        } catch (t: Throwable) {
            NSLog("⚠️ [AudioTts] Failed to speak: %@", t.message ?: "Unknown error")
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

