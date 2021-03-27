package com.visola_khudoynazarova.chattelegramm.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.visola_khudoynazarova.chattelegramm.R
import com.visola_khudoynazarova.chattelegramm.databinding.FragmentSplashBinding
import java.util.*


class SplashFragment : Fragment() {
    private lateinit var timer: Timer
    private lateinit var binding: FragmentSplashBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    private fun waitAndOpenOtherFragment() {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                findNavController().navigate(R.id.registrationFragment2)
            }

        }, 2000)

    }

    override fun onResume() {
        super.onResume()
        waitAndOpenOtherFragment()
    }


}