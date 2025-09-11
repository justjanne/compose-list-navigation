package org.example.project

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.add_reaction
import kotlinproject.composeapp.generated.resources.arrow_back
import kotlinproject.composeapp.generated.resources.call
import kotlinproject.composeapp.generated.resources.more_vert
import kotlinproject.composeapp.generated.resources.send
import kotlinproject.composeapp.generated.resources.settings
import kotlinproject.composeapp.generated.resources.videocam
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        val selectedChat = remember { mutableStateOf(SampleData.rooms.find { it.first == "Weltraum" }!!) }
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

                        val navigationState = rememberNavigationState(selectedChat.value.second, SampleData.roomItems)
                        val lazyListState = rememberListNavigationState(navigationState)
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.navigationList(navigationState, lazyListState),
                            contentPadding = PaddingValues(vertical = 12.dp),
                        ) {
                            items(SampleData.rooms) { room ->
                                val interactionSource = remember { MutableInteractionSource() }

                                ListItem(
                                    leadingContent = { Avatar(room.first.first().toString(), size = 40.dp) },
                                    headlineContent = {
                                        Text(room.first)
                                    },
                                    supportingContent = { Text("Lorem Ipsum I dolor sit amet") },
                                    modifier = Modifier
                                        .focusBorder(interactionSource)
                                        .navigationListItem(navigationState, room.second)
                                        .clickable(interactionSource, LocalIndication.current) {
                                            selectedChat.value = room
                                        }
                                )
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
                                label = selectedChat.value.first,
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
                val navigationState = rememberNavigationState(SampleData.messageItems.last(), SampleData.messageItems)
                val lazyListState = rememberListNavigationState(navigationState)

                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .navigationList(navigationState, lazyListState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(top = 120.dp, bottom = 16.dp)
                ) {
                    items(SampleData.messages, key = { it.first }) { (message, messageFocusRequester) ->
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
                            modifier = Modifier
                                .focusBorder(
                                    interactionSource,
                                    if (message.isMe) MessageBubbleDefaults.shapeOwn else MessageBubbleDefaults.shapeOther,
                                )
                                .navigationListItem(navigationState, messageFocusRequester)
                                .focusable(true, interactionSource)
                        ) {
                            Text(message.content, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
