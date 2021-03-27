package com.visola_khudoynazarova.chattelegramm.ui.activity.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.visola_khudoynazarova.chattelegramm.R
import com.visola_khudoynazarova.chattelegramm.databinding.MainFragmentBinding
import com.visola_khudoynazarova.chattelegramm.ui.activity.allChat.AllChatFragment
import com.visola_khudoynazarova.chattelegramm.ui.activity.joinGroup.GroupsFragment
import com.visola_khudoynazarova.chattelegramm.ui.activity.main.adapter.ViewPagerAdapter
import com.visola_khudoynazarova.chattelegramm.ui.activity.personal.PersonalFragment


class MainFragment : Fragment() {


    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)


        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.profileFragment
//            )
//        )

    }

    override fun onResume() {
        super.onResume()

        val adapter = ViewPagerAdapter(this)
        val titles = arrayOf("All", "Personal", "Group")
        adapter.addFragment(AllChatFragment(), "All")
        adapter.addFragment(PersonalFragment(), "Personal")
        adapter.addFragment(GroupsFragment(), "Group")
        binding.mainFAB.setOnClickListener {
            findNavController().navigate(R.id.contactsFragment)

        }
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = titles[position]
            tab.customView=binding.viewPager
        }
        binding.mainBottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profileFragment -> {
                    findNavController().navigate(R.id.profileFragment)
                }

            }
            false
        }
    }

}