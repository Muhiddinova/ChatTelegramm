package com.visola_khudoynazarova.chattelegramm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.visola_khudoynazarova.chattelegramm.model.MessageData

class MessageRepository {

    private val database = FirebaseDatabase.getInstance()

    fun getMessagesByGroupId(): LiveData<ArrayList<MessageData>> {

        val ref = database.getReference("messages")
        val messages = MutableLiveData<ArrayList<MessageData>>()

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<MessageData>()
                if (snapshot.exists() && snapshot.value != null){
                    for (data in snapshot.children){
                        list.add(data.getValue(MessageData::class.java)!!)
                    }
                }
                messages.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

       return messages

    }

}