package id.kotoba.app.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import kotlin.concurrent.thread

actual class AudioTtsEngine {
    actual fun speak(text: String, lang: String) {
        // Run asynchronously so UI is never blocked
        thread(isDaemon = true) {
            try {
                // Generate a pleasant melodic feedback tone for kana pronunciations on desktop
                val sampleRate = 44100f
                val frequency = when {
                    text.contains("あ") || text.contains("a") -> 523.25 // C5
                    text.contains("い") || text.contains("i") -> 587.33 // D5
                    text.contains("う") || text.contains("u") -> 659.25 // E5
                    text.contains("え") || text.contains("e") -> 698.46 // F5
                    text.contains("お") || text.contains("o") -> 783.99 // G5
                    else -> 659.25
                }
                val durationMs = 250
                val numSamples = (durationMs * sampleRate / 1000).toInt()
                val buffer = ByteArray(numSamples)

                for (i in 0 until numSamples) {
                    val angle = 2.0 * Math.PI * i / (sampleRate / frequency)
                    val envelope = Math.sin(Math.PI * i / numSamples) // smooth envelope
                    buffer[i] = (Math.sin(angle) * 100 * envelope).toInt().toByte()
                }

                val format = AudioFormat(sampleRate, 8, 1, true, true)
                val line = AudioSystem.getSourceDataLine(format)
                line.open(format)
                line.start()
                line.write(buffer, 0, buffer.size)
                line.drain()
                line.close()
            } catch (e: Exception) {
                println("[Kotoba AudioTts] desktop playback info: $text ($lang)")
            }
        }
    }

    actual fun stop() {
        // No-op for brief feedback chime
    }
}

@Composable
actual fun rememberAudioTtsEngine(): AudioTtsEngine {
    return remember { AudioTtsEngine() }
}
