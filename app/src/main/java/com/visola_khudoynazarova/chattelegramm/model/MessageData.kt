package com.visola_khudoynazarova.chattelegramm.model

import java.text.SimpleDateFormat
import java.util.*

data class MessageData(
    val senderUid: String = "",
    var message: String = "",
    val date: String = SimpleDateFormat(" HH:mm", Locale.getDefault()).format(Date()),
    val mediaPath: String = "",
    val type: String = ""

)