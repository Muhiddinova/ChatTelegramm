package com.visola_khudoynazarova.chattelegramm.ui.splash.registration

import androidx.lifecycle.ViewModel
import com.visola_khudoynazarova.chattelegramm.repository.UserDataRepository

class GetUserDataViewModel : ViewModel() {
   private val TAG="GetUserDataViewModel"
    private var repo:UserDataRepository?=null
    private var result:String?=null
    init {
        repo=UserDataRepository.getInstance()
    }

    fun uploadData(name: String, status: String){
       repo?.uploadData(name,status)
    }
}