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
    private var pendingText: String? = null
    private var pendingLang: String = "ja"

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
            pendingText?.let {
                speak(it, pendingLang)
                pendingText = null
            }
        }
    }

    actual fun speak(text: String, lang: String) {
        if (isInitialized) {
            val locale = if (lang == "ja") Locale.JAPANESE else Locale.US
            tts?.language = locale
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "kotoba_${System.currentTimeMillis()}")
        } else {
            pendingText = text
            pendingLang = lang
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
