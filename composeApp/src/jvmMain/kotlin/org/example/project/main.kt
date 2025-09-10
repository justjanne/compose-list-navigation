package org.example.project

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val state = rememberWindowState(size = DpSize(800.dp * 2, 600.dp * 2))
    Window(
        state = state,
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
        CompositionLocalProvider(
            LocalDensity provides LocalDensity.current.let {
                Density(it.density * 2, it.fontScale * 1)
            }
        ) {
            App()
        }
    }
}