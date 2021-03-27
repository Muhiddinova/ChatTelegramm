package com.visola_khudoynazarova.chattelegramm.ui.activity.joinGroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.visola_khudoynazarova.chattelegramm.model.Group
import com.visola_khudoynazarova.chattelegramm.repository.GroupRepository


class GroupsViewModel : ViewModel() {

    private val repo = GroupRepository.StaticFunction.getInstance()
    fun getGroup():LiveData<List<Group>>{
        return repo.getGroup()
    }


//    fun getGroupsById(): LiveData<ArrayList<Group>> {
//        return repo.getGroupsById()
//    }
//
//    fun setGroup(group: Group){
//        groupRepo.setGroup(group)
//    }

}