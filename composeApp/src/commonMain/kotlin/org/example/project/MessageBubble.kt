package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import compose_list_navigation.composeapp.generated.resources.Res
import compose_list_navigation.composeapp.generated.resources.add_reaction
import compose_list_navigation.composeapp.generated.resources.more_vert
import org.example.project.MessageBubbleDefaults.shapeOther
import org.example.project.MessageBubbleDefaults.shapeOwn
import org.jetbrains.compose.resources.vectorResource
import sun.security.krb5.internal.KDCOptions.with

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
    childModifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource,
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
            Row(modifier = Modifier.hoverable(interactionSource)) {
                val expanded = remember { mutableStateOf(false) }

                Surface(
                    color = if (isMe) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp),
                    contentColor = if (isMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                    shape = if (isMe) shapeOwn else shapeOther,
                    modifier = Modifier.focusBorder(interactionSource, if (isMe) shapeOwn else shapeOther).weight(1f, false),
                ) {
                    Box(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        content()
                    }
                }

                Spacer(Modifier.requiredWidth(4.dp))

                MessageMenu(
                    modifier = modifier,
                    expanded = expanded,
                    interactionSource = interactionSource,
                ) {
                    DropdownMenuItem(
                        text = { Text("Reply") },
                        onClick = {},
                    )
                    DropdownMenuItem(
                        text = { Text("React") },
                        onClick = {},
                    )
                    DropdownMenuItem(
                        text = { Text("Info") },
                        onClick = {},
                    )
                    DropdownMenuItem(
                        text = { Text("Report") },
                        onClick = {},
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                SuggestionChip(
                    onClick = {},
                    label = { Text("❤\uFE0F 1") },
                    shape = CircleShape,
                    modifier = childModifier,
                )
                SuggestionChip(
                    onClick = {},
                    label = { Text("\uD83D\uDC4D 1") },
                    shape = CircleShape,
                    modifier = childModifier,
                )
                SuggestionChip(
                    onClick = {},
                    label = { Icon(vectorResource(Res.drawable.add_reaction), contentDescription = null, modifier = Modifier.requiredSize(24.dp)) },
                    shape = CircleShape,
                    modifier = childModifier,
                )
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

@Composable
fun MessageMenu(
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean> = remember { mutableStateOf(true) },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit,
) {
    val focus = interactionSource.collectIsFocusedAsState()
    val hover = interactionSource.collectIsHoveredAsState()
    val isVisible: MutableTransitionState<Boolean> = remember { MutableTransitionState(expanded.value || focus.value || hover.value) }
    LaunchedEffect(expanded.value, focus.value, hover.value) {
        isVisible.targetState = expanded.value || focus.value || hover.value
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        IconButton(
            onClick = { expanded.value = true },
            interactionSource = interactionSource,
            modifier = modifier.focusBorder(interactionSource, CircleShape)
                .requiredSize(with (LocalDensity.current) { LocalTextStyle.current.lineHeight.toDp() + 12.dp }),
        ) {
            AnimatedVisibility(
                visibleState = isVisible,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Icon(vectorResource(Res.drawable.more_vert), contentDescription = "Actions")
            }
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            content = content,
        )
    }
}