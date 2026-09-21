package id.kotoba.app

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Katoba - Belajar Bahasa Jepang Anime",
        icon = painterResource(Res.drawable.katoba),
        state = rememberWindowState(width = 440.dp, height = 860.dp)
    ) {
        App()
    }
}
