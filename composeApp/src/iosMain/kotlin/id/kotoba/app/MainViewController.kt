package id.kotoba.app

import androidx.compose.ui.window.ComposeUIViewController
import kotlin.experimental.ExperimentalNativeApi
import platform.Foundation.NSLog
import platform.UIKit.UIViewController

@OptIn(ExperimentalNativeApi::class)
fun MainViewController(): UIViewController = ComposeUIViewController {
    // Pasang hook SEBELUM App() dikomposisi agar exception Kotlin native tertangkap
    setUnhandledExceptionHook { throwable ->
        NSLog("💥 [KOTOBA KOTLIN CRASH] Type: %@", throwable::class.simpleName ?: "Unknown")
        NSLog("💥 [KOTOBA KOTLIN CRASH] Message: %@", throwable.message ?: "No message")
        NSLog("💥 [KOTOBA KOTLIN CRASH] Cause: %@", throwable.cause?.message ?: "No cause")
        NSLog("💥 [KOTOBA KOTLIN CRASH] StackTrace:\n%@", throwable.stackTraceToString())
        // Kembalikan true agar proses tidak langsung abort (memberi waktu log tercetak)
        // Catatan: setelah hook selesai, runtime tetap akan terminate, tapi log sudah tercetak
    }

    App()
}
