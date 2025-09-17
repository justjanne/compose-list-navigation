package org.example.project

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.input.key.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.collections.get

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
    default: Any? = null,
    up: RovingFocusState.() -> Any?,
    down: RovingFocusState.() -> Any?,
    scroll: suspend CoroutineScope.(Any?) -> Unit = {},
): Modifier {
    val state = LocalRovingFocus.current
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(state, default) {
        val item = state.activeRef.value ?: default
        state.selectItem(item) { scroll(item) }
    }

    return this then Modifier
        .onFocusEvent {
            state.hasFocus = it.hasFocus
            if (it.isFocused) {
                val item = state.activeRef.value ?: default
                state.selectItem(item) { scroll(item) }
            }
        }
        .focusProperties {
            val item = state.activeRef.value ?: default
            if (state.references[item] != null) {
                canFocus = false
            }
        }
        .focusBorder(interactionSource)
        .focusable(interactionSource = interactionSource)
        .onKeyEvent { event ->
            when (event.key) {
                Key.DirectionUp -> {
                    if (event.type == KeyEventType.KeyDown) {
                        val item = state.up()
                        state.selectItem(item) { scroll(item) }
                    }
                    true
                }

                Key.DirectionDown -> {
                    if (event.type == KeyEventType.KeyDown) {
                        val item = state.down()
                        state.selectItem(item) { scroll(item) }
                    }
                    true
                }

                Key.DirectionLeft -> true
                Key.DirectionRight -> true

                else -> false
            }
        }
}

@Composable
fun Modifier.horizontalRovingFocus(
    default: Any? = null,
    left: RovingFocusState.() -> Any?,
    right: RovingFocusState.() -> Any?,
    scroll: suspend CoroutineScope.(Any?) -> Unit = {},
): Modifier {
    val state = LocalRovingFocus.current
    val interactionSource = remember { MutableInteractionSource() }

    return this then Modifier
        .onFocusEvent {
            state.hasFocus = it.hasFocus
            if (it.isFocused) {
                val item = state.activeRef.value ?: default
                state.selectItem(item) { scroll(item) }
            }
        }
        .focusProperties {
            val item = state.activeRef.value ?: default
            if (state.references[item] != null) {
                canFocus = false
            }
        }
        .focusBorder(interactionSource)
        .focusable(interactionSource = interactionSource)
        .onKeyEvent { event ->
            when (event.key) {
                Key.DirectionLeft -> {
                    if (event.type == KeyEventType.KeyDown) {
                        val item = state.left()
                        state.selectItem(item) { scroll(item) }
                    }
                    true
                }

                Key.DirectionRight -> {
                    if (event.type == KeyEventType.KeyDown) {
                        val item = state.right()
                        state.selectItem(item) { scroll(item) }
                    }
                    true
                }

                Key.DirectionUp -> true
                Key.DirectionDown -> true

                else -> false
            }
        }
}