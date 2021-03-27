package com.visola_khudoynazarova.chattelegramm.ui.activity.createGroup

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.visola_khudoynazarova.chattelegramm.R
import com.visola_khudoynazarova.chattelegramm.databinding.CreateGroupFragmentBinding
import com.visola_khudoynazarova.chattelegramm.model.Users
import com.visola_khudoynazarova.chattelegramm.ui.activity.conversation.ConversationFragment
import com.visola_khudoynazarova.chattelegramm.ui.utils.RecyclerViewClick

const val KEY="contactsList"
class CreateGroupFragment : Fragment(), RecyclerViewClick {
    private val TAG = "CreateGroupFragment"

    private lateinit var binding: CreateGroupFragmentBinding
    private lateinit var dialog: Dialog
    private lateinit var createGroupViewModel: CreateGroupViewModel

    companion object {
        fun newInstance(serializableList: List<Users>) = CreateGroupFragment().apply {
            arguments = bundleOf(KEY to serializableList)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.create_group_fragment, container, false)
        createGroupViewModel = ViewModelProvider(this).get(CreateGroupViewModel::class.java)
        binding.lifecycleOwner = this

        initRecyclerView()
        createGroupNameDialog()
        startConversationActivity()
        dialog = Dialog(requireActivity())

        return binding.root
    }

    private fun initRecyclerView() {

        val usersList = arguments?.getParcelableArrayList<Users>(KEY)!!.distinct()
        Log.d(TAG, "initRecyclerView: $usersList")
        if (usersList!= null) {
            val contactsAdapter =
                CreateGroupAdapter(usersList as ArrayList<Users>)
            binding.groupRV.apply {
                layoutManager =
                    LinearLayoutManager(
                        requireActivity().applicationContext,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                adapter = contactsAdapter
            }
            val checkedList = contactsAdapter.getCheckedList()
            Log.d(TAG, "initRecyclerView: $checkedList")
            createGroupViewModel.getMyInfo(checkedList)
            binding.groupFAB.setOnClickListener {
                if (checkedList.size < 1)
                    Toast.makeText(
                        requireActivity().applicationContext,
                        "Minimum Contacts is two!!",
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    dialog.show()
            }
        }
    }


    private fun createGroupNameDialog() {
        createGroupViewModel.createGroupMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val list = it
                dialog.setContentView(R.layout.group_name_dialog)
                val groupName = dialog.findViewById(R.id.groupName) as TextView
                val createGroup = dialog.findViewById(R.id.createGroup) as Button
                createGroup.setOnClickListener {
                    if (groupName.text.isEmpty())
                        Toast.makeText(
                            requireActivity(),
                            "Please specify a group name..",
                            Toast.LENGTH_SHORT
                        ).show()
                    else {
                        createGroupViewModel.createNewGroup(list, groupName.text.toString())
                        dialog.dismiss()
                    }
                }
            }
        })
    }

    private fun startConversationActivity() {
        createGroupViewModel.openConversation.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val conversationFragment =
                    ConversationFragment.newInstance(it.first, it.second, "", "group", "")
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, conversationFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
    }


}


//class CreateGroupFragment : Fragment(),RecyclerViewClick {
//
//    companion object {
//        fun newInstance(serializableList: List<Users>) = CreateGroupFragment().apply {
//            arguments = bundleOf("contactsList" to serializableList)
//        }
//    }
//
//    private lateinit var binding: CreateGroupFragmentBinding
//    private lateinit var viewModel: CreateGroupViewModel
//    private lateinit var dialog: Dialog
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//       binding=DataBindingUtil.inflate(inflater,R.layout.create_group_fragment, container, false)
//
//        viewModel = ViewModelProvider(this).get(CreateGroupViewModel::class.java)
//        binding.lifecycleOwner=this
////        initRecyclerView()
//        createGroupNameDialog()
//        startConversationActivity()
//        dialog = Dialog(requireActivity())
//        return binding.root
//    }
//
//    private fun startConversationActivity() {
//        TODO("Not yet implemented")
//    }
//
//    private fun createGroupNameDialog() {
//        TODO("Not yet implemented")
//    }

//    private fun initRecyclerView() {
//        val usersList = requireArguments().getParcelableArrayList<Users>("contactsList")!!.distinct()
//        Log.d("TEst", "initRecyclerView: " + usersList.size)
//        val contactsAdapter =
//            CreateGroupAdapter(usersList as ArrayList<Users>)
//        binding.groupRV.apply {
//            layoutManager =
//                LinearLayoutManager(
//                    activity!!.applicationContext,
//                    LinearLayoutManager.VERTICAL,
//                    false
//                )
//            adapter = contactsAdapter
//        }
//        val checkedList = contactsAdapter.getCheckedList()
//        createGroupViewModel.getMyInfo(checkedList)
//        binding.groupFAB.setOnClickListener {
//            if (checkedList.size < 1)
//                Toast.makeText(
//                    activity!!.applicationContext,
//                    "Minimum Contacts is two!!",
//                    Toast.LENGTH_SHORT
//                ).show()
//            else
//                dialog.show()
//        }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        // TODO: Use the ViewModel
//    }
//
//}