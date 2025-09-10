package org.example.project

import androidx.compose.ui.focus.FocusRequester

object SampleData {
    data class Message(
        val sender: String,
        val content: String,
        val showSender: Boolean,
        val isMe: Boolean,
    )

    val rooms = listOf(
        "Headroom",
        "Broom",
        "Weltraum",
        "Traum",
        "Vroom",
        "Chatroom",
    ).map { Pair(it, FocusRequester()) }

    val roomItems = rooms.map { it.second }

    val messages = listOf(
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

    val messageItems = messages.map { it.second }
}
