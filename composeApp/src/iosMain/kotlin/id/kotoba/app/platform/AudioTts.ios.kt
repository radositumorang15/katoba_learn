@file:OptIn(ExperimentalForeignApi::class)

package id.kotoba.app.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVSpeechBoundary
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance
import platform.Foundation.NSLog
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

interface NativeAudioSpeaker {
    fun speak(text: String, lang: String)
    fun stop()
}

object NativeAudioHub {
    var speaker: NativeAudioSpeaker? = null
}

actual class AudioTtsEngine {
    companion object {
        private var fallbackSynthesizer: AVSpeechSynthesizer? = null
        private var activeUtterance: AVSpeechUtterance? = null

        private fun getOrCreateSynthesizer(): AVSpeechSynthesizer {
            val existing = fallbackSynthesizer
            if (existing != null) return existing
            val synth = AVSpeechSynthesizer()
            fallbackSynthesizer = synth
            return synth
        }
    }

    actual fun speak(text: String, lang: String) {
        if (text.isBlank()) return

        // 1. Delegate to native Swift speaker if registered (best reliability & native ARC memory safety)
        val nativeSpeaker = NativeAudioHub.speaker
        if (nativeSpeaker != null) {
            try {
                nativeSpeaker.speak(text, lang)
                return
            } catch (t: Throwable) {
                NSLog("⚠️ [AudioTts] Native speaker delegate error: %@", t.message ?: "Unknown")
            }
        }

        // 2. Safe Fallback in Kotlin/Native on Main Thread
        dispatch_async(dispatch_get_main_queue()) {
            try {
                val synth = getOrCreateSynthesizer()

                // Only stop if actively speaking to avoid iOS 17/18 internal exception
                try {
                    if (synth.isSpeaking()) {
                        synth.stopSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)
                    }
                } catch (_: Throwable) {}

                val utterance = AVSpeechUtterance.speechUtteranceWithString(text)
                activeUtterance = utterance

                val voiceCode = if (lang == "ja") "ja-JP" else "en-US"
                val voice = AVSpeechSynthesisVoice.voiceWithLanguage(voiceCode)
                if (voice != null) {
                    utterance.setVoice(voice)
                }

                utterance.setVolume(1.0f)
                utterance.setRate(0.46f)
                utterance.setPitchMultiplier(1.0f)

                synth.speakUtterance(utterance)
            } catch (t: Throwable) {
                NSLog("⚠️ [AudioTts fallback] speak error: %@", t.message ?: "Unknown")
            }
        }
    }

    actual fun stop() {
        try {
            val nativeSpeaker = NativeAudioHub.speaker
            if (nativeSpeaker != null) {
                nativeSpeaker.stop()
                return
            }

            dispatch_async(dispatch_get_main_queue()) {
                try {
                    fallbackSynthesizer?.let { synth ->
                        if (synth.isSpeaking()) {
                            synth.stopSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)
                        }
                    }
                } catch (_: Throwable) {}
            }
        } catch (_: Throwable) {}
    }
}

@Composable
actual fun rememberAudioTtsEngine(): AudioTtsEngine {
    return remember { AudioTtsEngine() }
}



