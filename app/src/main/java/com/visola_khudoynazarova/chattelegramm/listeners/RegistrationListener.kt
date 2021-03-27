package com.visola_khudoynazarova.chattelegramm.listeners

import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

interface RegistrationListener {
    fun signInSuccess(user: FirebaseUser?)
    fun signInFailure(exception: Exception)
    fun createAccountSuccess(user: FirebaseUser?)
    fun createAccountFailure(exception: Exception)
}