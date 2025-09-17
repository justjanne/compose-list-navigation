package org.example.project

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Avatar(content: String, size: Dp) {
    Surface(
        modifier = Modifier.size(size),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.background),
        shadowElevation = 1.dp,
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.67f),
        shape = CircleShape,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                content,
                textAlign = TextAlign.Center,
                fontSize = size.value.div(2).sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}