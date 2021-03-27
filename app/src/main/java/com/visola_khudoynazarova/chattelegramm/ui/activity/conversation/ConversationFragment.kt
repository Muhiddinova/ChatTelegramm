package com.visola_khudoynazarova.chattelegramm.ui.activity.conversation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.visola_khudoynazarova.chattelegramm.R
import com.visola_khudoynazarova.chattelegramm.databinding.ConversationFragmentBinding
import com.visola_khudoynazarova.chattelegramm.model.ConversationInfo
import java.io.File

class   ConversationFragment : Fragment() {

    private lateinit var conversationAdapter: ConversationAdapter
    private lateinit var userUid: String
    private lateinit var chatID: String
    private lateinit var chatType: String
    private lateinit var binding: ConversationFragmentBinding
    private lateinit var conversationViewModel: ConversationViewModel


    companion object {
        fun newInstance(
            ChatID: String,
            userName: String,
            userImage: String,
            chatType: String,
            userUid: String
        ): ConversationFragment {
            val fragment = ConversationFragment()
            val args = Bundle()
            args.putString("ChatID", ChatID)
            args.putString("userName", userName)
            args.putString("userUid", userUid)
            args.putString("userImage", userImage)
            args.putString("chatType", chatType)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.conversation_fragment, container, false)

        fragmentArguments()
        conversationViewModel =
            ViewModelProvider(
                this, ConversationFactory(
                    ConversationInfo(
                        userUid, chatID,
                        chatType,
                        File(requireActivity().getExternalFilesDir(null), "records")), requireActivity().contentResolver
                )
            ).get(ConversationViewModel::class.java)
        binding.conversationVM = conversationViewModel
        binding.lifecycleOwner = this

        fillAdapterData()
        initViewListeners()
        initContactsRecyclerView()
        usersImages()
        toastMessages()
        unseenMessages()
        return binding.root
    }


    private fun fillAdapterData() {
        conversationViewModel.chatMessages().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            conversationAdapter.addMessage(it)
            binding.chatMessagesRV.scrollToPosition(conversationAdapter.itemCount - 1)
        })
    }

    private fun toastMessages() {
        conversationViewModel.toastMessages.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity().applicationContext, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun unseenMessages() {
        conversationViewModel.unseenMessages.observe(viewLifecycleOwner, Observer {
            conversationAdapter.unSeenMessages(it)
        })
    }


    private fun usersImages() {
        conversationViewModel.usersData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            conversationAdapter.addUsersImage(it.first)
            conversationAdapter.addUsersName(it.second)
        })
    }










    private fun fragmentArguments() {
        chatID = arguments?.getString("ChatID")!!
        val userName = arguments?.getString("userName")!!
        chatType = arguments?.getString("chatType")!!
        userUid = arguments?.getString("userUid", "")!!
          binding.userNameTxt.text = userName
    }


    private fun initViewListeners() {
        binding.cameraButton.setOnClickListener { chooseImage() }
        binding.backPress.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
    }


    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101)
    }





    private fun initContactsRecyclerView() {
        conversationAdapter =
            ConversationAdapter(
                chatType,
                conversationViewModel
            )
        binding.chatMessagesRV.apply {
            layoutManager =
                LinearLayoutManager(
                    requireActivity().applicationContext,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = conversationAdapter
        }
    }






}