package org.example.project

import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.util.fastAny

@Composable
fun rememberNavigationState(initial: FocusRequester, items: List<FocusRequester>): NavigationState {
    val current = remember { mutableStateOf<FocusRequester?>(null) }
    val itemState = remember(items) { mutableStateOf(items) }
    return remember(current, items) { NavigationState(initial, current, itemState) }
}

@Immutable
data class NavigationState(
    val initial: FocusRequester,
    val current: MutableState<FocusRequester?>,
    val items: State<List<FocusRequester>>,
) {
    val active: FocusRequester get() = current.value ?: initial
    val activeIndex: Int get() = items.value.indexOf(active)
}

@Composable
fun Modifier.navigationList(state: NavigationState, lazyListState: LazyListState): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val selectedItemVisible = remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.fastAny { it.index == state.activeIndex }
        }
    }
    val isFocused = interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused.value, selectedItemVisible, state.activeIndex) {
        if (isFocused.value && !selectedItemVisible.value) {
            lazyListState.scrollToItem(state.activeIndex)
            state.active.requestFocus()
        }
    }

    return this then Modifier
        .focusGroup()
        .focusable(!selectedItemVisible.value, interactionSource)
        .onPreviewKeyEvent { event ->
            when (event.key) {
                Key.DirectionUp -> {
                    if (event.type == KeyEventType.KeyDown) {
                        val index = state.activeIndex
                        if (index != -1) {
                            val newIndex = index.minus(1).coerceIn(state.items.value.indices)
                            state.current.value = state.items.value[newIndex]
                        }
                    }
                    true
                }

                Key.PageUp -> {
                    if (event.type == KeyEventType.KeyDown) {
                        val index = state.activeIndex
                        if (index != -1) {
                            val newIndex = index.minus(lazyListState.layoutInfo.visibleItemsInfo.size)
                                .coerceIn(state.items.value.indices)
                            state.current.value = state.items.value[newIndex]
                        }
                    }
                    true
                }

                Key.MoveHome -> {
                    if (event.type == KeyEventType.KeyDown) {
                        state.current.value = state.items.value.first()
                    }
                    true
                }

                Key.DirectionDown -> {
                    if (event.type == KeyEventType.KeyDown) {
                        val index = state.activeIndex
                        if (index != -1) {
                            val newIndex = index.plus(1).coerceIn(state.items.value.indices)
                            state.current.value = state.items.value[newIndex]
                        }
                    }
                    true
                }

                Key.PageDown -> {
                    if (event.type == KeyEventType.KeyDown) {
                        val index = state.activeIndex
                        if (index != -1) {
                            val newIndex = index.plus(lazyListState.layoutInfo.visibleItemsInfo.size)
                                .coerceIn(state.items.value.indices)
                            state.current.value = state.items.value[newIndex]
                        }
                    }
                    true
                }

                Key.MoveEnd -> {
                    if (event.type == KeyEventType.KeyDown) {
                        state.current.value = state.items.value.last()
                    }
                    true
                }

                else -> false
            }
        }
}

@Composable
fun rememberListNavigationState(navigationState: NavigationState): LazyListState {
    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = navigationState.activeIndex
    )
    LaunchedEffect(lazyListState, navigationState.activeIndex) {
        if (lazyListState.firstVisibleItemIndex != navigationState.activeIndex) {
            lazyListState.scrollToItem(navigationState.activeIndex)
        }
    }
    return lazyListState
}

@Composable
fun Modifier.navigationListItem(state: NavigationState, requester: FocusRequester): Modifier {
    LaunchedEffect(state.current.value) {
        if (state.current.value == requester) {
            requester.requestFocus()
        }
    }

    return this then Modifier
        .focusRequester(requester)
        .focusProperties { canFocus = state.active == requester }
}