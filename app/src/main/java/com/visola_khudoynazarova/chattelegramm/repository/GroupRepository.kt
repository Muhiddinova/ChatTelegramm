package com.visola_khudoynazarova.chattelegramm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.visola_khudoynazarova.chattelegramm.model.Group


class GroupRepository {
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var liveDataGroup: MutableLiveData<List<Group>>? = null
    private lateinit var databaseReference: DatabaseReference


    object StaticFunction {
        private var instance: GroupRepository? = null
        fun getInstance(): GroupRepository {
            if (instance == null)
                instance = GroupRepository()
            return instance!!
        }
    }


    fun getGroup(): LiveData<List<Group>> {
        if (liveDataGroup == null)
            liveDataGroup = MutableLiveData()
        databaseReference =
            FirebaseDatabase.getInstance().getReference("group").child(firebaseAuth.uid!!)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val group = listOf(snapshot.getValue(Group::class.java)!!)
                    liveDataGroup!!.postValue(group)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return liveDataGroup!!
    }
}


//    private val database = FirebaseDatabase.getInstance()
//
//    fun getGroupsById(): LiveData<ArrayList<Group>> {
//        val ref = database.getReference("groups")
//        val groups = MutableLiveData<ArrayList<Group>>()
//
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val list = arrayListOf<Group>()
////                if (snapshot.exists() && snapshot.value != null) {
////                    for (data in snapshot.children) {
////                        list.add(data.getValue(Group::class.java)!!)
////                    }
////                    groups.value = list
////                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        })
//        return groups
//    }
//
//    fun setGroup(group: Group) {
//
//        val ref = database.getReference("groups")
//
//        ref.setValue(group)
//            .addOnSuccessListener {
//                Log.e("GroupRepository", "setGroup: Success")
//            }
//            .addOnCanceledListener {
//                Log.d("GroupFragment", "setGroup: Cancelled")
//            }
//            .addOnFailureListener {
//                Log.e("GroupRepository", "setGroup: failure $it")
//            }
//
//    }

