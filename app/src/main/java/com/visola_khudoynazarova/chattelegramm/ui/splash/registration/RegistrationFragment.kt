package com.visola_khudoynazarova.chattelegramm.ui.splash.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.PhoneAuthProvider
import com.visola_khudoynazarova.chattelegramm.R
import com.visola_khudoynazarova.chattelegramm.databinding.RegistrationFragmentBinding

class RegistrationFragment : Fragment() {

    companion object {
        fun newInstance(code: String) = RegistrationFragment()
    }

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var binding: RegistrationFragmentBinding
//    private val navController by lazy(LazyThreadSafetyMode.NONE) {
//        NavHostFragment.findNavController(
//            this
//        )
//    }
    private var number: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.registration_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        observeAction()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            if (checkNumber()) {
                //show progress dialog
                viewModel.sendVerificationCode(binding.editNumber.text.toString())

//
            }
        }

    }

    private fun observeAction() {
        viewModel.result.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                // hide progress dialog
               findNavController().navigate(R.id.verifyNumberFragment, bundleOf("ver_id" to it))

            }
        })

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = RegistrationViewModelFactory(requireActivity())
        viewModel = ViewModelProvider(this, factory).get(RegistrationViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

    }

    private fun checkNumber(): Boolean {
        number = binding.editNumber.text.toString().trim()
        if (number!!.isEmpty()) {
            binding.editNumber.error = "Field is required"
            return false
        } else if (number!!.length < 13) {
            binding.editNumber.error = "Number should be 12 in length"
            return false
        }
        else return true
    }
}