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
import com.google.firebase.auth.PhoneAuthProvider
import com.visola_khudoynazarova.chattelegramm.R
import com.visola_khudoynazarova.chattelegramm.databinding.VerifyNumberFragmentBinding

class VerifyNumberFragment : Fragment() {
    private var code: String? = null
    private lateinit var binding: VerifyNumberFragmentBinding
    private var pin: String = ""
    private val TAG = "VerifyNumberFragment"


    private lateinit var viewModel: VerifyNumberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.verify_number_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VerifyNumberViewModel::class.java)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        binding.btnVerify.setOnClickListener {
            if (checkPin()) {
                val code = arguments?.getString("ver_id")
                Log.d(TAG, "onResume: $code")
                viewModel.signInUser(PhoneAuthProvider.getCredential(code!!, pin))
                Log.d(TAG, "onResume: $code")

                findNavController().navigate(R.id.getUserDataFragment)
            }
        }
    }


    private fun checkPin(): Boolean {
        pin = binding.otpTextView.text.toString()
        if (pin.isEmpty()) {
            binding.otpTextView.error = "Filed is required"
            return false
        } else if (pin.length < 6) {
            binding.otpTextView.error = "Enter valid pin"
            return false
        } else return true
    }

}