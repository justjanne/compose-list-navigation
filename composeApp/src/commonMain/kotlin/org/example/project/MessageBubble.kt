package org.example.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.MessageBubbleDefaults.shapeOther
import org.example.project.MessageBubbleDefaults.shapeOwn

object MessageBubbleDefaults {
    val shapeOwn = RoundedCornerShape(
        topStart = 24.dp,
        topEnd = 0.dp,
        bottomEnd = 24.dp,
        bottomStart = 24.dp
    )

    val shapeOther = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 24.dp,
        bottomEnd = 24.dp,
        bottomStart = 24.dp
    )
}

@Composable
fun MessageBubble(
    isMe: Boolean,
    showSender: Boolean,
    avatar: @Composable () -> Unit,
    sender: @Composable () -> Unit,
    timestamp: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val padding = if (showSender) PaddingValues(
        top = 16.dp,
        bottom = 1.dp
    ) else PaddingValues(vertical = 1.dp)
    Row(Modifier.padding(padding).widthIn(max = 960.dp)) {
        if (isMe) {
            Spacer(Modifier.width(112.dp))
        } else {
            Box(Modifier.width(56.dp), contentAlignment = Alignment.Center) {
                if (showSender) {
                    avatar()
                }
            }
        }
        Column(
            Modifier.weight(1f, true),
            horizontalAlignment = if (isMe) Alignment.End else Alignment.Start,
        ) {
            if (showSender) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    sender()
                    timestamp()
                }
                Spacer(Modifier.height(2.dp))
            }
            Surface(
                color = if (isMe) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
                contentColor = if (isMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                shape = if (isMe) shapeOwn else shapeOther,
                modifier = modifier,
            ) {
                Box(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    content()
                }
            }
        }
        if (isMe) {
            Box(Modifier.width(56.dp), contentAlignment = Alignment.Center) {
                if (showSender) {
                    avatar()
                }
            }
        } else {
            Spacer(Modifier.width(112.dp))
        }
    }
}