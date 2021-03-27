package com.visola_khudoynazarova.chattelegramm.ui.activity.allChat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.visola_khudoynazarova.chattelegramm.R

class AllChatFragment : Fragment() {

    companion object {
        fun newInstance() = AllChatFragment()
    }

    private lateinit var viewModel: AllChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.all_chat_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AllChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}