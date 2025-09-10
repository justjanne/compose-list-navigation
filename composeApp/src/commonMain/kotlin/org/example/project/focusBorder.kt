package org.example.project

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.focusBorder(
    interactionSource: InteractionSource,
    shape: Shape = RectangleShape,
): Modifier {
    val hasFocus = interactionSource.collectIsFocusedAsState()
    return this then Modifier
        .border(
            width = 2.dp,
            color = if (hasFocus.value) MaterialTheme.colorScheme.primary else Color.Transparent,
            shape = shape,
        )
        .border(
            width = 4.dp,
            color = if (hasFocus.value) MaterialTheme.colorScheme.onPrimary else Color.Transparent,
            shape = shape,
        )
}
