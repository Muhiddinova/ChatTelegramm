package com.visola_khudoynazarova.chattelegramm.ui.utils

import com.visola_khudoynazarova.chattelegramm.model.Users

interface RecyclerViewClick {
    fun onItemClickedPosition(data: Users){

    }
    fun onChatClickedString(key: String, userName:String, userImage: String,chatType:String, userUid: String){

    }
    fun openUserImage(userImage: String, userName: String){

    }
    fun receivedNewMessage(){

    }
}