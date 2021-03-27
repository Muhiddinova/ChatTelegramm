package com.visola_khudoynazarova.chattelegramm.ui.splash.registration

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.visola_khudoynazarova.chattelegramm.repository.RegistrationRepository

class RegistrationViewModel(activity: Activity) : ViewModel() {
    private val TAG = "RegistrationViewModel"
    private var repo: RegistrationRepository? = null


    init {
        repo = RegistrationRepository.getInstance(activity)
    }

    val result = repo!!.verificationIdLiveData
    val progressLiveData = MutableLiveData<Boolean>()

    private val _codLiveDate = MutableLiveData<String>()

    fun sendVerificationCode(phone: String) {
        progressLiveData.postValue(true)
        repo?.sendVerificationCode(phone)
    }

    fun code() {


    }
}