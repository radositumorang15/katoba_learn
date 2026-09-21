package id.kotoba.app.platform

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

actual class AudioTtsEngine : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    fun init(context: Context) {
        if (tts == null) {
            tts = TextToSpeech(context.applicationContext, this)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.JAPANESE)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.language = Locale.US
            }
            isInitialized = true
        }
    }

    actual fun speak(text: String, lang: String) {
        if (isInitialized) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "kotoba_${System.currentTimeMillis()}")
        }
    }

    actual fun stop() {
        tts?.stop()
    }

    fun release() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isInitialized = false
    }
}

@Composable
actual fun rememberAudioTtsEngine(): AudioTtsEngine {
    val context = LocalContext.current
    val engine = remember { AudioTtsEngine() }
    DisposableEffect(context, engine) {
        engine.init(context)
        onDispose {
            engine.release()
        }
    }
    return engine
}
