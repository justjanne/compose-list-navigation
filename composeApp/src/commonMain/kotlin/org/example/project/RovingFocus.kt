package org.example.project

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.input.key.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RovingFocusState(
    val coroutineScope: CoroutineScope,
    val activeRef: MutableState<Any?> = mutableStateOf(null),
    val references: SnapshotStateMap<Any, FocusRequester> = mutableStateMapOf(),
) {
    var hasFocus: Boolean = false

    @PublishedApi
    internal var isFocussing: Boolean = false

    fun selectItem(item: Any?, shouldFocus: Boolean = hasFocus, scroll: suspend CoroutineScope.() -> Unit = {}) {
        activeRef.value = item
        if (shouldFocus) {
            references[item]?.let {
                withFocus {
                    it.requestFocus(FocusDirection.Down)
                }
                coroutineScope.launch(block = scroll)
            } ?: coroutineScope.launch {
                scroll()
                withFocus {
                    references[item]?.requestFocus(FocusDirection.Down)
                }
            }
        }
    }

    inline fun withFocus(crossinline handler: () -> Unit) {
        isFocussing = true
        try {
            handler()
        } finally {
            isFocussing = false
        }
    }
}

val LocalRovingFocus = staticCompositionLocalOf<RovingFocusState> { error("Local RovingFocus state not provided.") }

@Composable
fun RovingFocusContainer(content: @Composable () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    CompositionLocalProvider(
        LocalRovingFocus provides remember { RovingFocusState(coroutineScope) },
        content = content,
    )
}

@Composable
fun Modifier.rovingFocusItem(key: Any, default: Any? = null): Modifier {
    val focusRequester = remember { FocusRequester() }
    val rovingFocusState = LocalRovingFocus.current

    DisposableEffect(key) {
        rovingFocusState.references[key] = focusRequester
        onDispose {
            rovingFocusState.references.remove(key)
        }
    }

    return this then Modifier
        .focusProperties {
            val current = rovingFocusState.activeRef.value ?: default
            val focusable = rovingFocusState.isFocussing || current == key
            if (!focusable) {
                canFocus = false
            }
        }
        .focusRequester(focusRequester)
}

@Composable
fun Modifier.rovingFocusChild(key: Any, default: Any? = null): Modifier {
    val rovingFocusState = LocalRovingFocus.current
    return this then Modifier
        .focusProperties {
            val current = rovingFocusState.activeRef.value ?: default
            val focusable = rovingFocusState.isFocussing || current == key
            if (!focusable) {
                canFocus = false
            }
        }
}

@Composable
fun Modifier.verticalRovingFocus(
    up: RovingFocusState.() -> Unit,
    down: RovingFocusState.() -> Unit,
): Modifier {
    val rovingFocusState = LocalRovingFocus.current

    return this then Modifier
        .onFocusEvent {
            rovingFocusState.hasFocus = it.hasFocus
        }
        .onKeyEvent { event ->
            when (event.key) {
                Key.DirectionUp -> {
                    if (event.type == KeyEventType.KeyDown) rovingFocusState.up()
                    true
                }

                Key.DirectionDown -> {
                    if (event.type == KeyEventType.KeyDown) rovingFocusState.down()
                    true
                }

                else -> false
            }
        }
}

@Composable
fun Modifier.horizontalRovingFocus(
    left: RovingFocusState.() -> Unit,
    right: RovingFocusState.() -> Unit,
): Modifier {
    val rovingFocusState = LocalRovingFocus.current

    return this then Modifier
        .onFocusEvent {
            rovingFocusState.hasFocus = it.hasFocus
        }
        .onKeyEvent { event ->
            when (event.key) {
                Key.DirectionLeft -> {
                    if (event.type == KeyEventType.KeyDown) rovingFocusState.left()
                    true
                }

                Key.DirectionRight -> {
                    if (event.type == KeyEventType.KeyDown) rovingFocusState.right()
                    true
                }

                else -> false
            }
        }
}