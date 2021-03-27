package com.visola_khudoynazarova.chattelegramm.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.visola_khudoynazarova.chattelegramm.model.Group
import com.visola_khudoynazarova.chattelegramm.model.Users

class CreateGroupRepository {


    private val openConversation = MutableLiveData<Pair<String, String>>()
        val createGroupMutableLiveData = MutableLiveData<ArrayList<Users>>()


        fun createNewGroup(list: List<Users>, groupName:String) {
            val key = FirebaseDatabase.getInstance().reference.child("Chats").push().key!!
            val chat = Group(
                chatID = key,
                lastMessage = "$groupName is Created",
                unreadMessage = usedHashMap(list, 0),
                usersPhone = usedHashMap(list, 1),
                offlineUserName = String(),
//                usersImage = usedHashMap(list, 2),
                mediaType = String(),
                lastSender = String(),
                userUid = String(),
                chatType = "group",
                groupName = groupName,
                groupImage = ""
            )
            FirebaseDatabase.getInstance().reference.child("Chats").child(key).child("Info")
                .setValue(chat)
            addChatKeyToUsers(list, key, groupName)
        }

        private fun addChatKeyToUsers(list: List<Users>, key: String, groupName: String) {
            for (i in list) {
                FirebaseDatabase.getInstance().reference.child("Users")
                    .child(i.uid)
                    .child("group").push().setValue(key)
            }
            openConversation.value = Pair(key, groupName)
        }


        fun getMyInfo(list: ArrayList<Users>) {
            FirebaseDatabase.getInstance().reference.child("Users")
                .child(FirebaseAuth.getInstance().uid!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        return
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val ui = p0.child("Uid").getValue(String::class.java)!!
                        val phone = p0.child("Phone").getValue(String::class.java)!!
                        val image = p0.child("Image").getValue(String::class.java)!!
//                        list.add(Users( "","", phone, ui, true))

                        createGroupMutableLiveData.value = list
                    }
                })
        }

        private fun usedHashMap(list: List<Users>, type: Int): HashMap<String, String> {
            val map = HashMap<String, String>()
            for (i in list.indices) {
                when (type) {
                    0 -> map[list[i].uid] = "0"
                    1 -> map[list[i].uid] = list[i].number
//                    2 -> map[list[i].uid] = list[i].image
                }

            }
            return map
        }

}