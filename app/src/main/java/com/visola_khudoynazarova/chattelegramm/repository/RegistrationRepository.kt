package com.visola_khudoynazarova.chattelegramm.repository

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.visola_khudoynazarova.chattelegramm.model.Users
import java.util.concurrent.TimeUnit

@Suppress("NAME_SHADOWING")
class RegistrationRepository(
    private var firebaseAuth: FirebaseAuth,
    private var activity: Activity
) {
    private val TAG = "RegistrationRepository"


    private var verificationId: String? = null
     val verificationIdLiveData = MutableLiveData<String?>()

    companion object {
        var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        private var INSTANCE: RegistrationRepository? = null
        private var databaseReference: DatabaseReference? = null

        fun getInstance(activity: Activity): RegistrationRepository {
            if (INSTANCE != null) {
                return INSTANCE!!
            }

            firebaseAuth = FirebaseAuth.getInstance()
            databaseReference = FirebaseDatabase.getInstance().getReference("Users")

            INSTANCE = RegistrationRepository(firebaseAuth, activity)
            return INSTANCE!!
        }
    }

    fun sendVerificationCode(phone: String) {

        val options = PhoneAuthOptions.newBuilder()
            .setActivity(activity)
            .setTimeout(60, TimeUnit.SECONDS)
            .setPhoneNumber(phone)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    Log.d("RepositoryId", "" + verificationId)
                    verificationIdLiveData.value=verificationId
                    this@RegistrationRepository.verificationId = verificationId
                    super.onCodeSent(verificationId, token)
                }

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    val smsCode = credential.smsCode
                    if (smsCode != null) {
                        val credential = PhoneAuthProvider.getCredential(checkNotNull(verificationId!!), smsCode)
                        signInWithCredential(credential)
                        Toast.makeText(activity, "$smsCode", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "onVerificationCompleted: $smsCode")
                    }

                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener {

                    }

                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    Toast.makeText(
                        activity,
                        "onVerification failure $exception",
                        Toast.LENGTH_LONG
                    ).show()

                    Log.e("MainActivity", "onVerificationFailed: $exception")

                }


            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)


    }




    private fun signInWithCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                val user =
                    Users(
                        "", "",
                        firebaseAuth.currentUser!!.phoneNumber!!,
                        firebaseAuth.uid!!,true
                    )

                databaseReference!!.child(firebaseAuth.uid!!).setValue(user)


                Toast.makeText(activity, "sign in success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "sign in failure $it", Toast.LENGTH_LONG).show()
            }
            .addOnCanceledListener {
                Toast.makeText(activity, "sign in cancelled", Toast.LENGTH_SHORT).show()
            }

    }


}