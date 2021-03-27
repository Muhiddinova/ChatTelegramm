package com.visola_khudoynazarova.chattelegramm.ui.activity.conversation

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.visola_khudoynazarova.chattelegramm.model.ConversationInfo

@Suppress("UNCHECKED_CAST")
class ConversationFactory constructor(
    private val conversationInfo: ConversationInfo,
    private val contentResolver: ContentResolver
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ConversationViewModel::class.java)) {
            ConversationViewModel(contentResolver, conversationInfo) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}