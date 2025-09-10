package org.example.project

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
fun Avatar(content: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.size(40.dp),
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
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

private val rooms = listOf(
    "Headroom",
    "Broom",
    "Weltraum",
    "Traum",
    "Vroom",
    "Chatroom",
).map { Pair(it, FocusRequester()) }

private val messages = listOf(
    "Verse1" to "If you got time to lean",
    "Verse1" to "Then you got time to clean",
    "Verse1" to "is what they told me",
    "Verse1" to "But They don't own me",
    "Verse1" to "They just own the only",
    "Verse1" to "thing that's keeping me alive",
    "Verse1" to "They swear that's not a threat",
    "Verse1" to "Cause I could always starve to death",
    "Verse1" to "I guess I'll Pledge allegiance to the paycheck",
    "Verse2" to "So I'll sign the dotted line",
    "Verse2" to "Cross my t's and dot my I's",
    "Verse2" to "I'll say the customer's always right",
    "Verse2" to "Cross my heart and hope to die",
    "Verse2" to "Turn my frown upside down",
    "Verse2" to "Lock my knees til I pass out",
    "Verse2" to "Sold my soul for the payroll",
    "Verse3" to "Just don't ask me why cause",
    "Verse3" to "I don't know",
    "Verse3" to "I just work here I suppose",
    "Verse3" to "I don't care",
    "Verse3" to "you can burn it down as far as I'm concerned",
    "Verse3" to "Call it apathy or maybe I'm just lazy",
    "Verse3" to "I don't know!",
    "Verse3" to "But It's still me myself and I against the world",
    "Verse4" to "Maybe I'll win the lottery",
    "Verse4" to "Buy myself a lobotomy",
    "Verse4" to "Scoop out my brains and maybe then",
    "Verse4" to "I'll blend right in with management",
    "Verse4" to "Pretty, rich or talented",
    "Verse4" to "You need two to make it",
    "Verse4" to "I guess I'll just pledge Allegiance",
    "Verse4" to "To the paycheck",
    "Verse5" to "They'll say that's just the way",
    "Verse5" to "the real world works",
    "Verse5" to "Everything's supposed to hurt",
    "Verse5" to "They Idolize self sacrifice",
    "Verse5" to "And demonize self worth",
    "Verse5" to "And all the while they'll just",
    "Verse5" to "sit back and ask why",
    "Verse5" to "No one wants to work",
    "Verse5" to "And we'll all have a hardy laugh",
    "Verse5" to "Roll our eyes and catch our breath",
    "Verse5" to "All together with our chest",
    "Verse5" to "we'll send it back and tell them",
    "Verse5" to "I don't know I just work here",
).let { list ->
    buildList {
        var previousMessage: Message? = null
        for ((sender, content) in list) {
            val message = Message(sender, content, sender != previousMessage?.sender, sender == "Verse3")
            previousMessage = message
            add(Pair(message, FocusRequester()))
        }
    }
}

data class Message(
    val sender: String,
    val content: String,
    val showSender: Boolean,
    val isMe: Boolean,
)

val roomList = rooms.map { it.second }
val messageList = messages.map { it.second }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        val selectedChat = remember { mutableStateOf(rooms.find { it.first == "Weltraum" }!!) }
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet {
                    Column {
                        TopAppBar(
                            title = {
                                val interactionSource = remember { MutableInteractionSource() }
                                val hasFocus = interactionSource.collectIsFocusedAsState()
                                Surface(
                                    onClick = {},
                                    shape = CircleShape,
                                    interactionSource = interactionSource,
                                    modifier = Modifier
                                        .border(2.dp, if (hasFocus.value) Color.Red else Color.Transparent)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(
                                            start = 4.dp,
                                            top = 4.dp,
                                            bottom = 4.dp,
                                            end = 24.dp
                                        ),
                                    ) {
                                        Avatar("J", modifier = Modifier.size(32.dp))
                                        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                                        Text("justJanne", style = MaterialTheme.typography.titleMedium)
                                    }
                                }
                            },
                            actions = {
                                val interactionSource = remember { MutableInteractionSource() }
                                val hasFocus = interactionSource.collectIsFocusedAsState()

                                IconButton(
                                    onClick = {},
                                    interactionSource = interactionSource,
                                    modifier = Modifier
                                        .border(2.dp, if (hasFocus.value) Color.Red else Color.Transparent),
                                ) {
                                    Icon(
                                        vectorResource(Res.drawable.more_vert),
                                        contentDescription = "More",
                                    )
                                }
                            }
                        )
                        val roomFocus = rememberFocusState(selectedChat.value.second, roomList)
                        LazyColumn(
                            modifier = Modifier.customFocus(roomFocus),
                            contentPadding = PaddingValues(vertical = 12.dp),
                        ) {
                            items(rooms) { room ->
                                val (title, chatFocusRequester) = room
                                val interactionSource = remember { MutableInteractionSource() }
                                val hasFocus = interactionSource.collectIsFocusedAsState()
                                val activeRoom = (roomFocus.current.value ?: roomFocus.initial)
                                ListItem(
                                    leadingContent = { Avatar(title.first().toString()) },
                                    headlineContent = {
                                        Text(
                                            title,
                                            color = if (hasFocus.value) Color.Red else LocalContentColor.current
                                        )
                                    },
                                    supportingContent = { Text("Lorem Ipsum I dolor sit amet") },
                                    modifier = Modifier
                                        .border(2.dp, if (hasFocus.value) Color.Red else Color.Transparent)
                                        .focusRequester(chatFocusRequester)
                                        .focusProperties {
                                            canFocus = activeRoom == chatFocusRequester
                                        }
                                        .clickable(interactionSource, LocalIndication.current) {
                                            selectedChat.value = room
                                        }
                                )

                                LaunchedEffect(roomFocus.current.value) {
                                    if (roomFocus.current.value == chatFocusRequester) {
                                        chatFocusRequester.requestFocus()
                                    }
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
                            val interactionSource = remember { MutableInteractionSource() }
                            val hasFocus = interactionSource.collectIsFocusedAsState()
                            IconButton(
                                onClick = {},
                                interactionSource = interactionSource,
                                modifier = Modifier
                                    .border(2.dp, if (hasFocus.value) Color.Red else Color.Transparent),
                            ) {
                                Icon(
                                    vectorResource(Res.drawable.arrow_back),
                                    contentDescription = "Back",
                                )
                            }
                        },
                        title = {
                            val interactionSource = remember { MutableInteractionSource() }
                            val hasFocus = interactionSource.collectIsFocusedAsState()
                            Surface(
                                onClick = {},
                                shape = CircleShape,
                                interactionSource = interactionSource,
                                modifier = Modifier
                                    .border(2.dp, if (hasFocus.value) Color.Red else Color.Transparent),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 4.dp, end = 24.dp),
                                ) {
                                    val (chatTitle, _) = selectedChat.value
                                    Avatar(chatTitle.first().toString(), modifier = Modifier.size(32.dp))
                                    Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                                    Text(chatTitle, style = MaterialTheme.typography.titleMedium)
                                }
                            }
                        },
                        actions = {
                            TooltipBox(
                                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                                tooltip = { PlainTooltip { Text("Call") } },
                                state = rememberTooltipState(),
                            ) {
                                val interactionSource = remember { MutableInteractionSource() }
                                val hasFocus = interactionSource.collectIsFocusedAsState()

                                IconButton(
                                    onClick = {},
                                    interactionSource = interactionSource,
                                    modifier = Modifier
                                        .border(2.dp, if (hasFocus.value) Color.Red else Color.Transparent),
                                ) {
                                    Icon(
                                        vectorResource(Res.drawable.call),
                                        contentDescription = "Call",
                                    )
                                }
                            }
                            TooltipBox(
                                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                                tooltip = { PlainTooltip { Text("Video Call") } },
                                state = rememberTooltipState(),
                            ) {
                                val interactionSource = remember { MutableInteractionSource() }
                                val hasFocus = interactionSource.collectIsFocusedAsState()

                                IconButton(
                                    onClick = {},
                                    interactionSource = interactionSource,
                                    modifier = Modifier
                                        .border(2.dp, if (hasFocus.value) Color.Red else Color.Transparent),
                                ) {
                                    Icon(
                                        vectorResource(Res.drawable.videocam),
                                        contentDescription = "Video Call",
                                    )
                                }
                            }
                            TooltipBox(
                                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                                tooltip = { PlainTooltip { Text("Options") } },
                                state = rememberTooltipState(),
                            ) {
                                val interactionSource = remember { MutableInteractionSource() }
                                val hasFocus = interactionSource.collectIsFocusedAsState()

                                IconButton(
                                    onClick = {},
                                    interactionSource = interactionSource,
                                    modifier = Modifier
                                        .border(2.dp, if (hasFocus.value) Color.Red else Color.Transparent),
                                ) {
                                    Icon(
                                        vectorResource(Res.drawable.settings),
                                        contentDescription = "Options",
                                    )
                                }
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar {
                        val inputState = remember { mutableStateOf(TextFieldValue()) }

                        TooltipBox(
                            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                            tooltip = { PlainTooltip { Text("Add Emoji") } },
                            state = rememberTooltipState(),
                        ) {
                            val interactionSource = remember { MutableInteractionSource() }
                            val hasFocus = interactionSource.collectIsFocusedAsState()

                            IconButton(
                                onClick = {},
                                interactionSource = interactionSource,
                                modifier = Modifier
                                    .border(2.dp, if (hasFocus.value) Color.Red else Color.Transparent),
                            ) {
                                Icon(
                                    vectorResource(Res.drawable.add_reaction),
                                    contentDescription = "Add Emoji",
                                )
                            }
                        }


                        val inputInteractionSource = remember { MutableInteractionSource() }
                        val inputHasFocus = inputInteractionSource.collectIsFocusedAsState()
                        OutlinedTextField(
                            value = inputState.value,
                            onValueChange = { inputState.value = it },
                            interactionSource = inputInteractionSource,
                            modifier = Modifier.weight(1f, true)
                                .border(2.dp, if (inputHasFocus.value) Color.Red else Color.Transparent)
                                .composed("inputFocusMover") {
                                    val focusManager = LocalFocusManager.current
                                    onPreviewKeyEvent {
                                        if (it.key == Key.Tab && !it.isCtrlPressed && !it.isMetaPressed && !it.isAltPressed) {
                                            if (it.type == KeyEventType.KeyDown) {
                                                focusManager.moveFocus(
                                                    if (it.isShiftPressed) FocusDirection.Previous
                                                    else FocusDirection.Next,
                                                )
                                            }
                                            true
                                        } else {
                                            false
                                        }
                                    }
                                }
                        )

                        TooltipBox(
                            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                            tooltip = { PlainTooltip { Text("Send") } },
                            state = rememberTooltipState(),
                        ) {
                            val interactionSource = remember { MutableInteractionSource() }
                            val hasFocus = interactionSource.collectIsFocusedAsState()
                            IconButton(
                                onClick = {},
                                interactionSource = interactionSource,
                                modifier = Modifier
                                    .border(2.dp, if (hasFocus.value) Color.Red else Color.Transparent),
                            ) {
                                Icon(
                                    vectorResource(Res.drawable.send),
                                    contentDescription = "Send",
                                )
                            }
                        }
                    }
                }
            ) { contentPadding ->
                val messageFocus = rememberFocusState(messages.last().second, messageList)
                val activeMessage = (messageFocus.current.value ?: messageFocus.initial)
                val lazyListState = rememberLazyListState(
                    initialFirstVisibleItemIndex = messages.indexOfFirst { it.second == activeMessage }
                )
                LaunchedEffect(lazyListState, activeMessage) {
                    val index = messages.indexOfFirst { it.second == activeMessage }
                    if (lazyListState.firstVisibleItemIndex != index) {
                        lazyListState.scrollToItem(index)
                    }
                }
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .customFocus(messageFocus),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(top = 120.dp, bottom = 16.dp)
                ) {
                    items(messages, key = { it.first }) { (message, messageFocusRequester) ->
                        key(message) {
                            val interactionSource = remember { MutableInteractionSource() }
                            val hasFocus = interactionSource.collectIsFocusedAsState()
                            val avatar = @Composable {
                                Box(Modifier.width(56.dp), contentAlignment = Alignment.Center) {
                                    if (message.showSender) {
                                        Avatar(message.sender.first().toString(), modifier = Modifier.size(32.dp))
                                    }
                                }
                            }
                            val padding = if (message.showSender) PaddingValues(
                                top = 16.dp,
                                bottom = 1.dp
                            ) else PaddingValues(vertical = 1.dp)
                            Row(Modifier.padding(padding).widthIn(max = 960.dp)) {
                                if (message.isMe) {
                                    Spacer(Modifier.width(112.dp))
                                } else {
                                    avatar()
                                }
                                Column(
                                    Modifier.weight(1f, true),
                                    horizontalAlignment = if (message.isMe) Alignment.End else Alignment.Start,
                                ) {
                                    if (message.showSender) {
                                        Text(
                                            message.sender,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(Modifier.height(2.dp))
                                    }
                                    Surface(
                                        color = if (message.isMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceColorAtElevation(
                                            8.dp
                                        ),
                                        contentColor = if (message.isMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                        shape = RoundedCornerShape(
                                            topStart = if (message.isMe) 24.dp else 0.dp,
                                            topEnd = if (message.isMe) 0.dp else 24.dp,
                                            bottomEnd = 24.dp,
                                            bottomStart = 24.dp
                                        ),
                                        modifier = Modifier
                                            .border(2.dp, if (hasFocus.value) Color.Red else Color.Transparent)
                                            .focusRequester(messageFocusRequester)
                                            .focusProperties {
                                                canFocus = activeMessage == messageFocusRequester
                                            }
                                            .focusable(true, interactionSource),
                                    ) {
                                        Box(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                                            Text(message.content, style = MaterialTheme.typography.bodyMedium)
                                        }
                                    }
                                }
                                if (message.isMe) {
                                    avatar()
                                } else {
                                    Spacer(Modifier.width(112.dp))
                                }
                            }

                            LaunchedEffect(messageFocus.current.value) {
                                if (messageFocus.current.value == messageFocusRequester) {
                                    messageFocusRequester.requestFocus()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun rememberFocusState(initial: FocusRequester, items: List<FocusRequester>): FocusState {
    val current = remember { mutableStateOf<FocusRequester?>(null) }
    val itemState = remember(items) { mutableStateOf(items) }
    return remember(current, items) { FocusState(initial, current, itemState) }
}

@Immutable
data class FocusState(
    val initial: FocusRequester,
    val current: MutableState<FocusRequester?>,
    val items: State<List<FocusRequester>>,
)

@Composable
fun Modifier.customFocus(state: FocusState): Modifier {
    val parentFocus = remember { mutableStateOf(false) }
    val childFocus = remember { mutableStateOf(false) }

    return this then Modifier
        .onFocusEvent {
            parentFocus.value = it.isFocused
            childFocus.value = it.hasFocus
        }
        .focusGroup()
        .onPreviewKeyEvent { event ->
            when (event.key) {
                Key.DirectionUp -> {
                    if (event.type == KeyEventType.KeyDown) {
                        val activeItem = state.current.value ?: state.initial
                        val index = state.items.value.indexOf(activeItem)
                        if (index != -1) {
                            state.current.value = state.items.value[index.minus(1).coerceIn(state.items.value.indices)]
                        }
                    }
                    true
                }

                Key.PageUp -> {
                    if (event.type == KeyEventType.KeyDown) {
                        val activeItem = state.current.value ?: state.initial
                        val index = state.items.value.indexOf(activeItem)
                        if (index != -1) {
                            state.current.value = state.items.value[index.minus(10).coerceIn(state.items.value.indices)]
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
                        val activeItem = state.current.value ?: state.initial
                        val index = state.items.value.indexOf(activeItem)
                        if (index != -1) {
                            state.current.value = state.items.value[index.plus(1).coerceIn(state.items.value.indices)]
                        }
                    }
                    true
                }

                Key.PageDown -> {
                    if (event.type == KeyEventType.KeyDown) {
                        val activeItem = state.current.value ?: state.initial
                        val index = state.items.value.indexOf(activeItem)
                        if (index != -1) {
                            state.current.value = state.items.value[index.plus(10).coerceIn(state.items.value.indices)]
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
        }.border(
            2.dp, when {
                parentFocus.value -> Color.Red
                childFocus.value -> Color.Green
                else -> Color.Transparent
            }
        )
}