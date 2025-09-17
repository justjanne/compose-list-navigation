package org.example.project

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose_list_navigation.composeapp.generated.resources.*
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
@Preview
fun App() {
    val rooms = remember { mutableStateOf(SampleData.rooms) }
    val messages = remember { mutableStateOf(SampleData.messages) }

    MaterialTheme {
        val selectedChat = remember { mutableStateOf(rooms.value.find { it.id == RoomId("#space") }!!.id) }
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet {
                    Column {
                        TopAppBar(
                            title = {
                                ProfileButton(
                                    label = "justJanne",
                                    onClick = {},
                                )
                            },
                            actions = {
                                ToolbarButton(
                                    label = "More",
                                    icon = vectorResource(Res.drawable.more_vert),
                                    onClick = {},
                                )
                            }
                        )


                        val defaultItem = selectedChat
                        val lazyListState = rememberLazyListState(rooms.value.indexOfFirst { it.id == defaultItem.value })

                        RovingFocusContainer {
                            val rovingFocusState = LocalRovingFocus.current
                            LaunchedEffect(rovingFocusState, defaultItem.value) {
                                val nextItem = defaultItem.value
                                rovingFocusState.selectItem(nextItem) {
                                    lazyListState.scrollToItem(rooms.value.indexOfFirst { it.id == nextItem })
                                }
                            }

                            LazyColumn(
                                state = lazyListState,
                                contentPadding = PaddingValues(vertical = 12.dp),
                                modifier = Modifier.rovingFocusContainer(
                                    up = {
                                        val currentItem = activeRef.value ?: defaultItem.value
                                        val currentIndex = rooms.value.indexOfFirst { it.id == currentItem }
                                        val nextIndex = currentIndex.minus(1).coerceIn(rooms.value.indices)
                                        val nextItem = rooms.value[nextIndex].id
                                        activeRef.value = nextItem
                                        selectItem(nextItem) {
                                            lazyListState.scrollToItem(rooms.value.indexOfFirst { it.id == nextItem })
                                        }
                                    },
                                    down = {
                                        val currentItem = activeRef.value ?: defaultItem.value
                                        val currentIndex = rooms.value.indexOfFirst { it.id == currentItem }
                                        val nextIndex = currentIndex.plus(1).coerceIn(rooms.value.indices)
                                        val nextItem = rooms.value[nextIndex].id
                                        activeRef.value = nextItem
                                        selectItem(nextItem) {
                                            lazyListState.scrollToItem(rooms.value.indexOfFirst { it.id == nextItem })
                                        }
                                    },
                                )
                            ) {
                                items(rooms.value, key = { it.id }) { room ->
                                    val interactionSource = remember { MutableInteractionSource() }

                                    ListItem(
                                        leadingContent = { Avatar(room.name.first().toString(), size = 40.dp) },
                                        headlineContent = {
                                            Text(room.name)
                                        },
                                        supportingContent = { Text("Lorem Ipsum I dolor sit amet") },
                                        tonalElevation = if (selectedChat.value == room.id) 4.dp else 0.dp,
                                        modifier = Modifier
                                            .rovingFocusItem(room.id, defaultItem.value)
                                            .focusBorder(interactionSource)
                                            .clickable(interactionSource, LocalIndication.current) {
                                                selectedChat.value = room.id
                                                rovingFocusState.selectItem(room.id, shouldFocus = true) {
                                                    lazyListState.scrollToItem(rooms.value.indexOfFirst { it.id == room.id })
                                                }
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            ToolbarButton(
                                label = "Back",
                                icon = vectorResource(Res.drawable.arrow_back),
                                onClick = {},
                            )
                        },
                        title = {
                            ProfileButton(
                                label = rooms.value.find { it.id == selectedChat.value }!!.name,
                                onClick = {},
                            )
                        },
                        actions = {
                            ToolbarButton(
                                label = "Call",
                                icon = vectorResource(Res.drawable.call),
                                onClick = {},
                            )

                            ToolbarButton(
                                label = "Video Call",
                                icon = vectorResource(Res.drawable.videocam),
                                onClick = {},
                            )

                            ToolbarButton(
                                label = "Options",
                                icon = vectorResource(Res.drawable.settings),
                                onClick = {},
                            )
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar {
                        ToolbarButton(
                            label = "Add Emoji",
                            icon = vectorResource(Res.drawable.add_reaction),
                            onClick = {},
                        )

                        Spacer(Modifier.width(8.dp))

                        val inputState = remember { mutableStateOf(TextFieldValue()) }
                        val inputInteractionSource = remember { MutableInteractionSource() }
                        OutlinedTextField(
                            value = inputState.value,
                            onValueChange = { inputState.value = it },
                            interactionSource = inputInteractionSource,
                            modifier = Modifier.weight(1f, true)
                                .focusBorder(inputInteractionSource, OutlinedTextFieldDefaults.shape)
                                .inputFocusNavigation()
                        )

                        Spacer(Modifier.width(8.dp))

                        FilledToolbarButton(
                            label = "Send",
                            icon = vectorResource(Res.drawable.send),
                            onClick = {},
                        )
                    }
                }
            ) { contentPadding ->
                val defaultItem = remember {
                    derivedStateOf {
                        messages.value.last().id
                    }
                }
                val lazyListState = rememberLazyListState(messages.value.indexOfFirst { it.id == defaultItem.value })

                RovingFocusContainer {
                    val rovingFocusState = LocalRovingFocus.current

                    LaunchedEffect(rovingFocusState, defaultItem.value) {
                        val nextItem = defaultItem.value
                        rovingFocusState.selectItem(nextItem) {
                            lazyListState.scrollToItem(messages.value.indexOfFirst { it.id == nextItem })
                        }
                    }

                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding)
                            .rovingFocusContainer(
                                up = {
                                    val currentItem = activeRef.value ?: defaultItem.value
                                    val currentIndex = messages.value.indexOfFirst { it.id == currentItem }
                                    val nextIndex = currentIndex.minus(1).coerceIn(messages.value.indices)
                                    val nextItem = messages.value[nextIndex].id
                                    activeRef.value = nextItem
                                    selectItem(nextItem) {
                                        lazyListState.scrollToItem(messages.value.indexOfFirst { it.id == nextItem })
                                    }
                                },
                                down = {
                                    val currentItem = activeRef.value ?: defaultItem.value
                                    val currentIndex = messages.value.indexOfFirst { it.id == currentItem }
                                    val nextIndex = currentIndex.plus(1).coerceIn(messages.value.indices)
                                    val nextItem = messages.value[nextIndex].id
                                    activeRef.value = nextItem
                                    selectItem(nextItem) {
                                        lazyListState.scrollToItem(messages.value.indexOfFirst { it.id == nextItem })
                                    }
                                },
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(top = 120.dp, bottom = 16.dp)
                    ) {
                        items(messages.value, key = { it }) { message ->
                            val interactionSource = remember { MutableInteractionSource() }

                            MessageBubble(
                                message.isMe,
                                message.showSender,
                                avatar = { Avatar(message.sender.first().toString(), size = 36.dp) },
                                sender = {
                                    Text(
                                        message.sender,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.paddingFromBaseline(top = 20.sp),
                                    )
                                },
                                timestamp = {
                                    Text(
                                        message.timestamp,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = LocalContentColor.current.copy(alpha = 0.67f),
                                        modifier = Modifier.paddingFromBaseline(top = 20.sp),
                                    )
                                },
                                modifier = Modifier.rovingFocusItem(message.id, defaultItem.value),
                                childModifier = Modifier.rovingFocusChild(message.id, defaultItem.value),
                                interactionSource = interactionSource,
                                onFocus = {
                                    rovingFocusState.selectItem(message.id, shouldFocus = true)
                                }
                            ) {
                                Text(message.content, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}