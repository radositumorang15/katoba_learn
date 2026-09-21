package id.kotoba.app.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import kotlin.concurrent.thread

actual class AudioTtsEngine {
    private val cacheDir = File(System.getProperty("java.io.tmpdir"), "kotoba_audio_cache").apply {
        mkdirs()
    }

    actual fun speak(text: String, lang: String) {
        // Run asynchronously so UI is never blocked
        thread(isDaemon = true) {
            val played = tryPlayJapaneseVoice(text, lang)
            if (!played) {
                playFallbackMelodicTone(text)
            }
        }
    }

    private fun tryPlayJapaneseVoice(text: String, lang: String): Boolean {
        return try {
            val encoded = URLEncoder.encode(text, "UTF-8")
            val targetFile = File(cacheDir, "${text.hashCode()}_${lang}.mp3")

            if (!targetFile.exists() || targetFile.length() == 0L) {
                val voiceUrl = "https://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=$lang&q=$encoded"
                val connection = java.net.URI(voiceUrl).toURL().openConnection() as HttpURLConnection
                connection.setRequestProperty("User-Agent", "Mozilla/5.0")
                connection.connectTimeout = 3000
                connection.readTimeout = 3000

                if (connection.responseCode == 200) {
                    connection.inputStream.use { input ->
                        targetFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                } else {
                    return false
                }
            }

            if (targetFile.exists() && targetFile.length() > 0L) {
                val isWindows = System.getProperty("os.name").lowercase().contains("win")
                if (isWindows) {
                    val filePath = targetFile.absolutePath.replace("'", "''")
                    val durationMs = maxOf(700, text.length * 280)
                    val cmd = "Add-Type -AssemblyName presentationCore; \$p = New-Object System.Windows.Media.MediaPlayer; \$p.Open([System.Uri]'$filePath'); \$p.Play(); Start-Sleep -Milliseconds $durationMs"
                    val pb = ProcessBuilder("powershell", "-NoProfile", "-NonInteractive", "-Command", cmd)
                    val process = pb.start()
                    process.waitFor()
                    return true
                }
            }
            false
        } catch (_: Exception) {
            false
        }
    }

    private fun playFallbackMelodicTone(text: String) {
        try {
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
                val envelope = Math.sin(Math.PI * i / numSamples)
                buffer[i] = (Math.sin(angle) * 100 * envelope).toInt().toByte()
            }

            val format = AudioFormat(sampleRate, 8, 1, true, true)
            val line = AudioSystem.getSourceDataLine(format)
            line.open(format)
            line.start()
            line.write(buffer, 0, buffer.size)
            line.drain()
            line.close()
        } catch (_: Exception) {}
    }

    actual fun stop() {}
}

@Composable
actual fun rememberAudioTtsEngine(): AudioTtsEngine {
    return remember { AudioTtsEngine() }
}
