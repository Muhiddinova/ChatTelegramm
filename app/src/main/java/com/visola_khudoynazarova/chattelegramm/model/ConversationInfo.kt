package com.visola_khudoynazarova.chattelegramm.model

import java.io.File

data class ConversationInfo (
    val userUid: String = "",
    val chatID: String = "",
    val chatType: String = "",
    val recordFile: File
)