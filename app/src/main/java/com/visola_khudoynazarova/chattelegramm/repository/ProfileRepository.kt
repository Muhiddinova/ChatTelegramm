package com.visola_khudoynazarova.chattelegramm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.visola_khudoynazarova.chattelegramm.model.Group
import com.visola_khudoynazarova.chattelegramm.model.Users

class ProfileRepository {
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var liveData: MutableLiveData<Users>? = null
    private var liveDataGroup: MutableLiveData<Group>? = null
    private lateinit var databaseReference: DatabaseReference


    object StaticFunction {
        private var instance: ProfileRepository? = null
        fun getInstance(): ProfileRepository {
            if (instance == null)
                instance = ProfileRepository()

            return instance!!
        }
    }

    fun getUser(): LiveData<Users> {

        if (liveData == null)
            liveData = MutableLiveData()
        databaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.uid!!)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userModel = snapshot.getValue(Users::class.java)
                    liveData!!.postValue(userModel)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ProfileRepository", "onCancelled: $error")
            }

        })

        return liveData!!
    }



}
