package com.visola_khudoynazarova.chattelegramm.ui.activity.joinGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.visola_khudoynazarova.chattelegramm.model.Group
import com.visola_khudoynazarova.chattelegramm.R
import com.visola_khudoynazarova.chattelegramm.databinding.JoinGroupFragmentBinding
import com.visola_khudoynazarova.chattelegramm.ui.activity.joinGroup.adapter.GroupAdapter
import java.util.*
import kotlin.collections.ArrayList

class GroupsFragment : Fragment() {

    private lateinit var viewModel: GroupsViewModel
    private lateinit var binding: JoinGroupFragmentBinding
    private lateinit var adapter: GroupAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.join_group_fragment, container, false)

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GroupsViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        viewModel.getGroup().observe(viewLifecycleOwner, androidx.lifecycle.Observer { group->
            setRv(group)

        })


    }

    private fun setRv(group: List<Group>) {
        adapter = GroupAdapter {

        }

        binding.rvGroups.adapter = adapter
        adapter.updateData(group)

    }

}