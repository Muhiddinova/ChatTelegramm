package com.visola_khudoynazarova.chattelegramm.ui.splash.registration

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthCredential
import com.visola_khudoynazarova.chattelegramm.repository.VerifyRepository

class VerifyNumberViewModel() : ViewModel() {
    private val TAG = "VerifyNumberViewModel"
    private var repo: VerifyRepository? = null

    init {
        repo = VerifyRepository.getInstance()
    }

    fun signInUser(credential: PhoneAuthCredential) {
        repo?.signInUser(credential)
    }
}