package com.visola_khudoynazarova.chattelegramm.model

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class Group (
    val chatID: String="",
    var lastMessage: String="",
    var lastMessageDate: String = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(
        Date()
    ),
    val unreadMessage: HashMap<String, String> = HashMap(),
    val usersPhone: HashMap<String, String> = HashMap(),
    var offlineUserName: String="",
//    val usersImage: HashMap<String, String> = HashMap(),
    var userUid: String="",
    val mediaType: String = "",
    val lastSender:String = "",
    val chatType:String = "",
    val groupName:String = "",
    val groupImage:String = ""
)