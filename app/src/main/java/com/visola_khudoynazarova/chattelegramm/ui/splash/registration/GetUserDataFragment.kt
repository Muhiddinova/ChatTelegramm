package com.visola_khudoynazarova.chattelegramm.ui.splash.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.visola_khudoynazarova.chattelegramm.R
import com.visola_khudoynazarova.chattelegramm.databinding.GetUserDataFragmentBinding

class GetUserDataFragment : Fragment() {

    private val TAG="GetUserFragment"
    private lateinit var username: String
    private lateinit var status: String
    private lateinit var binding: GetUserDataFragmentBinding

    companion object {
        fun newInstance() = GetUserDataFragment()
    }

    private lateinit var viewModel: GetUserDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.get_user_data_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GetUserDataViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        binding.btnDataDone.setOnClickListener {
            if (checkData()) {
                Log.d(TAG, "onResume: $username + $status")
                viewModel.uploadData(username,status)
                findNavController().navigate(R.id.main_nav)
            }
        }
    }


    private fun checkData(): Boolean {
        username = binding.edtUserName.text.toString().trim()
        status = binding.edtUserStatus.text.toString().trim()

        if (username.isEmpty()) {
            binding.edtUserName.error = "Filed is required"
            return false
        } else if (status.isEmpty()) {
            binding.edtUserStatus.error = "Filed is required"
            return false
        } else return true
    }

}