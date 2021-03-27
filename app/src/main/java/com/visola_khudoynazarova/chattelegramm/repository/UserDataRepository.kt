package com.visola_khudoynazarova.chattelegramm.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.visola_khudoynazarova.chattelegramm.model.Users

class UserDataRepository() {

    private val TAG = "UserDataRepository"
    companion object {
        var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        private var INSTANCE: UserDataRepository? = null
        private var databaseReference:DatabaseReference?=null

        fun getInstance():UserDataRepository {
            if (INSTANCE != null) {
                return INSTANCE!!
            }

            firebaseAuth = FirebaseAuth.getInstance()
            databaseReference = FirebaseDatabase.getInstance().getReference("Users")

            INSTANCE = UserDataRepository()
            return INSTANCE!!
        }
    }

    fun uploadData(name: String, status: String)  {
        databaseReference!!.child(firebaseAuth.uid !!).get()
            .addOnSuccessListener {
                val map = Users(
                    name, status,
                     firebaseAuth.currentUser!!.phoneNumber!!,
                   firebaseAuth.uid!!,true,""
                )
        databaseReference!!.child(firebaseAuth.uid!!).setValue(map)
            .addOnSuccessListener {

                Log.d(TAG, "uploadData: $map")


            }

    }}


}



