package com.visola_khudoynazarova.chattelegramm.ui.activity.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.visola_khudoynazarova.chattelegramm.model.Users
import com.visola_khudoynazarova.chattelegramm.repository.ProfileRepository

class ProfileViewModel : ViewModel() {

    private var repo = ProfileRepository.StaticFunction.getInstance()

    fun getUser(): LiveData<Users> {
        return repo.getUser()
    }
}