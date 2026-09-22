package id.kotoba.app

import androidx.compose.ui.window.ComposeUIViewController
import kotlin.experimental.ExperimentalNativeApi
import platform.Foundation.NSLog
import platform.UIKit.UIViewController

@OptIn(ExperimentalNativeApi::class)
fun MainViewController(): UIViewController {
    // Pasang exception hook segera sebelum ComposeUIViewController dibuat
    setUnhandledExceptionHook { throwable ->
        NSLog("💥 [KOTOBA KOTLIN CRASH] Type: %@", throwable::class.simpleName ?: "Unknown")
        NSLog("💥 [KOTOBA KOTLIN CRASH] Message: %@", throwable.message ?: "No message")
        NSLog("💥 [KOTOBA KOTLIN CRASH] Cause: %@", throwable.cause?.message ?: "No cause")
        NSLog("💥 [KOTOBA KOTLIN CRASH] StackTrace:\n%@", throwable.stackTraceToString())
    }

    return ComposeUIViewController(
        configure = {
            enforceStrictPlistSanityCheck = false
        }
    ) {
        App()
    }
}

