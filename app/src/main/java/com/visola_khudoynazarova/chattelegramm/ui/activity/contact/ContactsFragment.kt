package com.visola_khudoynazarova.chattelegramm.ui.activity.contact

import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.visola_khudoynazarova.chattelegramm.R
import com.visola_khudoynazarova.chattelegramm.databinding.ContactsFragmentBinding
import com.visola_khudoynazarova.chattelegramm.model.ConversationChatData
import com.visola_khudoynazarova.chattelegramm.model.Users
import com.visola_khudoynazarova.chattelegramm.ui.activity.conversation.ConversationFragment
import com.visola_khudoynazarova.chattelegramm.ui.activity.createGroup.CreateGroupFragment


class ContactsFragment : Fragment() {

    private val TAG = "ContactsFragment"

    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var binding: ContactsFragmentBinding
    private lateinit var contactsViewModel: ContactsViewModel
    


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = DataBindingUtil.inflate(inflater, R.layout.contacts_fragment, container, false)
        contactsViewModel =
            ViewModelProvider(this, getViewModelFactory()).get(ContactsViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }


    private fun getViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ContactsViewModel(
                    activity!!.contentResolver,
                    activity!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                ) as T
            }
        }
    }

    private fun initViews() {
        Log.d("ss", "HI")
//        binding.contactToolbar.logoutButton.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
//        binding.contactToolbar.toolbarName.text = resources.getString(R.string.contacts)
    }

    private fun createNewGroup() {
        binding.newGroup.setOnClickListener {
            contactsViewModel.getContactsLiveData().observe(this, Observer {
                requireActivity().supportFragmentManager.popBackStack()
                val fragment = CreateGroupFragment.newInstance(it)
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, fragment)
                    .addToBackStack(null)
                    .commit()
            })
        }
    }

    private fun fillContactsAdapter() {

        contactsViewModel.getContactsLiveData().observe(this, {
            Log.d(TAG, "fillContactsAdapter: $it")
            contactsAdapter.addContacts(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun initContactsRecyclerView() {
        contactsAdapter =
            ContactsAdapter(contactsViewModel)
        binding.contactsRV.apply {
            layoutManager =
                LinearLayoutManager(
                    requireActivity().applicationContext,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = contactsAdapter
        }
    }

    private fun startConversationActivity(conversationChatData: ConversationChatData) {
        val conversationFragment =
            ConversationFragment.newInstance(
                conversationChatData.key,
                conversationChatData.userName,
                conversationChatData.userImage,
                conversationChatData.chatType,
                conversationChatData.userUid
            )
        requireActivity().supportFragmentManager.beginTransaction()
            .add(android.R.id.content, conversationFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun observeConversationChat() {
        contactsViewModel.getConversationLiveData().observe(this, Observer {
            startConversationActivity(it)
        })
    }

    override fun onStart() {
        super.onStart()
        initViews()
        initContactsRecyclerView()
        contactsViewModel.getContactsList()
        createNewGroup()
        observeConversationChat()
        fillContactsAdapter()
    }
}