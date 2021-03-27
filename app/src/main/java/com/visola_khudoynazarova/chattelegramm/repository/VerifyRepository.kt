package com.visola_khudoynazarova.chattelegramm.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.database.DatabaseReference
import com.visola_khudoynazarova.chattelegramm.model.Users

class VerifyRepository (private var firebaseAuth: FirebaseAuth){
    private val TAG = "VerifyRepository"
    private var databaseReference: DatabaseReference? = null


    fun signInUser(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "signInUser: onsuccess")
//                val user = Users(
//                    "", "", "",
//                    firebaseAuth.currentUser!!.phoneNumber!!,
//                    firebaseAuth.uid!!
//                )
//                databaseReference!!.child(firebaseAuth.uid!!).setValue(user)

            } else {
                Log.d(TAG, "signInUser: ${it.exception?.message}")
            }
        }.addOnFailureListener {
            Log.d(TAG, "signInUser: onfail ${it.message}")
        }
    }

    companion object {
        var firebaseAuth:FirebaseAuth= FirebaseAuth.getInstance()
        private var INSTANCE: VerifyRepository? = null
        fun getInstance(): VerifyRepository {
            if (INSTANCE != null) {
                return INSTANCE!!
            }

            firebaseAuth= FirebaseAuth.getInstance()
            Log.d("TAG", "getInstance: $INSTANCE")
            INSTANCE= VerifyRepository(firebaseAuth)
            return INSTANCE!!





    }

}
}