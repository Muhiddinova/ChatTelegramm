package com.visola_khudoynazarova.chattelegramm.ui.activity.contact

import android.content.ContentResolver
import android.provider.ContactsContract
import android.telephony.TelephonyManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.visola_khudoynazarova.chattelegramm.model.ConversationChatData
import com.visola_khudoynazarova.chattelegramm.model.Group
import com.visola_khudoynazarova.chattelegramm.model.Users
import com.visola_khudoynazarova.chattelegramm.ui.utils.CountryISO

class ContactsViewModel(
    private val contentResolver: ContentResolver,
    private val telephonyManager: TelephonyManager
) : ViewModel() {

    private val TAG = "ContactsViewmodel"

    private lateinit var myPhoneNumber: String
    private val conversationChatData = MutableLiveData<ConversationChatData>()
    private val contactsMutableLiveData = MutableLiveData<ArrayList<Users>>()
    private val usersList = ArrayList<Users>()
    fun getContactsLiveData(): LiveData<ArrayList<Users>> = contactsMutableLiveData
    fun getConversationLiveData(): LiveData<ConversationChatData> = conversationChatData


    fun getContactsList() {
        getMyPhoneNumber("", null, false)
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                var number =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                number = number.replace(" ", "")
                number = number.replace("-", "")
                number = number.replace(")", "")
                number = number.replace("(", "")
                Log.d(TAG, "getContactsList: $number name: $name")
                if (number[0] != '+')
                    number = getCountryISO() + number

                val data = Users(name, "", number, "")
                getUserInfo(data)
            }
            cursor.close()
        }
    }

    private fun getUserInfo(userData: Users) {
        Log.d(TAG, "getUserInfo: called get user info")
        FirebaseDatabase.getInstance().reference.child("Users")
            .orderByChild("number").equalTo(userData.number)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        for (data in p0.children) {
                            Log.d(TAG, "getUserInfo: ${data.getValue(Users::class.java)}")
                            userData.status = data.child("status").value.toString()
                            userData.uid = data.child("uid").value.toString()
                            userData.haveAccount = true
                            userData.image = data.child("image").value.toString()

                            if (userData.number != myPhoneNumber)
                                usersList.add(userData)
                            Log.d(TAG, "userList: $usersList")
                        }
                        Log.d(TAG, "userList: $usersList")
                        contactsMutableLiveData.value = usersList
                        Log.d(TAG, "contactsMutableLivedata: ${contactsMutableLiveData.value}")
                    }
                }
            })
    }

    private fun getCountryISO(): String {
        var countryCodeValue = telephonyManager.networkCountryIso
        if (countryCodeValue == "")
            countryCodeValue = telephonyManager.simCountryIso

        return CountryISO.getPhone(countryCodeValue)!!
    }

    private fun reuseHashMap(
        uid1: String,
        uid2: String,
        value1: String,
        value2: String
    ): HashMap<String, String> {
        val phones = HashMap<String, String>()
        phones[uid1] = value1
        phones[uid2] = value2
        return phones
    }

    private fun addUsersData(key: String, myPhone: String, myImage: String, user: Users) {
        val data = Group(
            key,
            "",
            usersPhone = reuseHashMap(
                FirebaseAuth.getInstance().uid!!,
                user.uid,
                myPhone,
                user.number
            ),
            unreadMessage = reuseHashMap(
                FirebaseAuth.getInstance().uid!!,
                user.uid,
                "0",
                "0"
            ),

            userUid = user.uid
        )

        FirebaseDatabase.getInstance().reference.child("Chats").child(key)
            .child("Info").setValue(data)

    }


    private fun createNewConversation(user: Users): String {
        val key = FirebaseDatabase.getInstance().reference.child("chat").push().key!!
        FirebaseDatabase.getInstance().reference.child("Users")
            .child(FirebaseAuth.getInstance().uid!!)
            .child("chat").child(user.uid).setValue(key)
        FirebaseDatabase.getInstance().reference.child("Users").child(user.uid)
            .child("chat").child(FirebaseAuth.getInstance().uid!!).setValue(key)
        getMyPhoneNumber(key, user, true)
        return key
    }


    private fun getMyPhoneNumber(key: String, user: Users?, addUserPhone: Boolean) {
        FirebaseDatabase.getInstance().reference.child("Users")
            .child(FirebaseAuth.getInstance().uid!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    return
                }

                override fun onDataChange(p0: DataSnapshot) {
                    Log.d(TAG, "onDataChange: ${p0.getValue(Users::class.java)}")
                    myPhoneNumber = p0.child("number").getValue(String::class.java)!!
                    val myImage = p0.child("image").getValue(String::class.java)!!
                    if (addUserPhone)
                        addUsersData(key, myPhoneNumber, myImage, user!!)
                }
            })
    }


    fun checkConversationStatus(user: Users) {
        FirebaseDatabase.getInstance().reference.child("Users")
            .child(FirebaseAuth.getInstance().uid!!).child("chat")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    var isExist = false
                    var key = ""
                    if (p0.exists()) {
                        for (data in p0.children) {
                            if (data.key == user.uid) {
                                isExist = true
                                key = p0.child(data.key.toString()).getValue(String::class.java)!!
                                break
                            }
                        }
                    }
                    if (!isExist)
                        key = createNewConversation(user)

                    conversationChatData.value =
                        ConversationChatData(key, user.name, user.image, "direct", user.uid)
                }
            })
    }
}