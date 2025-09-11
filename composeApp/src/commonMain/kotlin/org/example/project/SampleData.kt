package org.example.project

import androidx.compose.ui.focus.FocusRequester

object SampleData {
    data class MessageData(
        val sender: String,
        val timestamp: String,
        val content: String,
    )

    data class Message(
        val sender: String,
        val timestamp: String,
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
        MessageData(sender="Verse", timestamp="00:03:05", content="If you got time to lean,"),
        MessageData(sender="Verse", timestamp="00:05:04", content="Then you got time to clean,"),
        MessageData(sender="Verse", timestamp="00:06:08", content="Is what they told me,"),
        MessageData(sender="Verse", timestamp="00:08:06", content="But they don't own me,"),
        MessageData(sender="Verse", timestamp="00:09:09", content="They just own the only thing that's keeping me alive,"),
        MessageData(sender="Verse", timestamp="00:13:03", content="They swear that's not a threat,"),
        MessageData(sender="Verse", timestamp="00:14:09", content="Cause I could always starve to death,"),
        MessageData(sender="Verse", timestamp="00:16:09", content="I guess I'll pledge allegiance to the paycheck"),

        MessageData(sender="Hook", timestamp="00:21:02", content="So I'll sign the dotted line,"),
        MessageData(sender="Hook", timestamp="00:23:05", content="Cross my T's and dot my I's,"),
        MessageData(sender="Hook", timestamp="00:25:06", content="Say the customer's always right,"),
        MessageData(sender="Hook", timestamp="00:27:07", content="Cross my heart and hope to die,"),
        MessageData(sender="Hook", timestamp="00:29:08", content="I'll turn my frown upside down,"),
        MessageData(sender="Hook", timestamp="00:32:02", content="Lock my knees 'til I pass out,"),
        MessageData(sender="Hook", timestamp="00:34:02", content="Sold my soul for the payroll,"),
        MessageData(sender="Hook", timestamp="00:36:07", content="Just don't ask me why cause"),

        MessageData(sender="Chorus", timestamp="00:38:02", content="I don't know,"),
        MessageData(sender="Chorus", timestamp="00:39:08", content="I just work here I suppose,"),
        MessageData(sender="Chorus", timestamp="00:42:04", content="I don't care,"),
        MessageData(sender="Chorus", timestamp="00:44:03", content="You can burn it down as far as I'm concerned,"),
        MessageData(sender="Chorus", timestamp="00:47:04", content="Call it apathy or maybe I'm just lazy,"),
        MessageData(sender="Chorus", timestamp="00:50:07", content="I don't know,"),
        MessageData(sender="Chorus", timestamp="00:51:06", content="But it's still me, myself, and I against the world,"),
        MessageData(sender="Chorus", timestamp="00:54:06", content="(me, myself and I against the world)"),

        MessageData(sender="Verse", timestamp="01:05:03", content="Maybe I'll win the lottery,"),
        MessageData(sender="Verse", timestamp="01:07:02", content="Buy myself a lobotomy,"),
        MessageData(sender="Verse", timestamp="01:09:04", content="Scoop out my brains and maybe then,"),
        MessageData(sender="Verse", timestamp="01:11:06", content="I'll blend right in with management,"),
        MessageData(sender="Verse", timestamp="01:14:00", content="Pretty, rich or talented,"),
        MessageData(sender="Verse", timestamp="01:16:03", content="You need two to make it,"),
        MessageData(sender="Verse", timestamp="01:18:00", content="I guess I'll just pledge allegiance to the paycheck"),

        MessageData(sender="Hook", timestamp="01:22:03", content="So I'll sign the dotted line,"),
        MessageData(sender="Hook", timestamp="01:24:05", content="Cross my T's and dot my I's,"),
        MessageData(sender="Hook", timestamp="01:26:06", content="Say the customer's always right,"),
        MessageData(sender="Hook", timestamp="01:28:07", content="Cross my heart and hope to die,"),
        MessageData(sender="Hook", timestamp="01:30:08", content="I'll turn my frown upside down,"),
        MessageData(sender="Hook", timestamp="01:33:01", content="Lock my knees 'til I pass out,"),
        MessageData(sender="Hook", timestamp="01:35:01", content="Sold my soul for the payroll,"),
        MessageData(sender="Hook", timestamp="01:37:05", content="Just don't ask me why cause"),

        MessageData(sender="Chorus", timestamp="01:39:03", content="I don't know,"),
        MessageData(sender="Chorus", timestamp="01:40:09", content="I just work here I suppose,"),
        MessageData(sender="Chorus", timestamp="01:43:05", content="I don't care,"),
        MessageData(sender="Chorus", timestamp="01:45:02", content="You can burn it down as far as I'm concerned,"),
        MessageData(sender="Chorus", timestamp="01:48:05", content="Call it apathy or maybe I'm just lazy,"),
        MessageData(sender="Chorus", timestamp="01:51:07", content="I don't know,"),
        MessageData(sender="Chorus", timestamp="01:52:08", content="But it's still me, myself, and I against the world,"),
        MessageData(sender="Chorus", timestamp="01:55:06", content="(me, myself and I against the world),"),
        MessageData(sender="Chorus", timestamp="01:57:09", content="(me, myself and I against the world)"),

        MessageData(sender="Interlude", timestamp="01:58:02", content="\"Excuse me young man, may I speak to your manager?\","),
        MessageData(sender="Interlude", timestamp="02:00:05", content="\"Uh, I don't think he's here right now\","),
        MessageData(sender="Interlude", timestamp="02:02:01", content="\"Well I am appalled,"),
        MessageData(sender="Interlude", timestamp="02:03:06", content="At the lack respect you're showing to your customers out here\","),
        MessageData(sender="Interlude", timestamp="02:05:08", content="\"Oh\","),
        MessageData(sender="Interlude", timestamp="02:06:03", content="\"I have been a loyal patron of this establishment for 30 years\","),
        MessageData(sender="Interlude", timestamp="02:09:05", content="\"Woah\","),
        MessageData(sender="Interlude", timestamp="02:09:09", content="\"And you have just lost my business,"),
        MessageData(sender="Interlude", timestamp="02:11:08", content="What do you have to say for yourself?\","),
        MessageData(sender="Interlude", timestamp="02:13:08", content="\"Uh, I mean I don't know man, I just work here\""),

        MessageData(sender="Bridge", timestamp="02:18:07", content="They'll say that's just the way the real world works,"),
        MessageData(sender="Bridge", timestamp="02:21:08", content="Everything's supposed to hurt,"),
        MessageData(sender="Bridge", timestamp="02:23:06", content="They Idolize self sacrifice,"),
        MessageData(sender="Bridge", timestamp="02:25:08", content="And demonize self worth,"),
        MessageData(sender="Bridge", timestamp="02:27:07", content="And all the while they'll just sit back and ask why,"),
        MessageData(sender="Bridge", timestamp="02:30:05", content="No one wants to work,"),
        MessageData(sender="Bridge", timestamp="02:32:00", content="We'll all have a hardy laugh,"),
        MessageData(sender="Bridge", timestamp="02:34:03", content="And roll our eyes and catch our breath,"),
        MessageData(sender="Bridge", timestamp="02:36:06", content="And all together with our chest,"),
        MessageData(sender="Bridge", timestamp="02:38:05", content="We'll send it back and tell them"),

        MessageData(sender="Chorus", timestamp="02:40:03", content="I don't know,"),
        MessageData(sender="Chorus", timestamp="02:41:08", content="I just work here I suppose,"),
        MessageData(sender="Chorus", timestamp="02:44:04", content="I don't care,"),
        MessageData(sender="Chorus", timestamp="02:46:04", content="You can burn it down as far as I'm concerned,"),
        MessageData(sender="Chorus", timestamp="02:49:05", content="Call it apathy or maybe I'm just lazy,"),
        MessageData(sender="Chorus", timestamp="02:52:08", content="I don't know,"),
        MessageData(sender="Chorus", timestamp="02:53:09", content="But it's still me, myself, and I against the world,"),
        MessageData(sender="Chorus", timestamp="02:56:08", content="(me, myself and I against the world)"),

        MessageData(sender="Hook", timestamp="02:58:06", content="So I'll sign the dotted line,"),
        MessageData(sender="Hook", timestamp="03:00:04", content="Cross my T's and dot my I's,"),
        MessageData(sender="Hook", timestamp="03:02:06", content="Say the customer's always right,"),
        MessageData(sender="Hook", timestamp="03:04:09", content="Cross my heart and hope to die,"),
        MessageData(sender="Hook", timestamp="03:06:08", content="I'll turn my frown upside down,"),
        MessageData(sender="Hook", timestamp="03:09:02", content="Lock my knees 'til I pass out,"),
        MessageData(sender="Hook", timestamp="03:11:04", content="Sold my soul for the payroll,"),
        MessageData(sender="Hook", timestamp="03:13:08", content="Just don't ask me why cause,"),
        MessageData(sender="Hook", timestamp="03:15:02", content="I don't know,"),
        MessageData(sender="Hook", timestamp="03:16:07", content="\"I hope you have a terrific day\""),
    ).let { list ->
        buildList {
            var previousMessage: Message? = null
            for (data in list) {
                val message = Message(
                    sender = data.sender,
                    timestamp = data.timestamp,
                    content = data.content,
                    showSender = data.sender != previousMessage?.sender,
                    isMe = data.sender == "Interlude"
                )
                previousMessage = message
                add(Pair(message, FocusRequester()))
            }
        }
    }

    val messageItems = messages.map { it.second }
}
