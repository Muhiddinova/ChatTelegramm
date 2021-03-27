package com.visola_khudoynazarova.chattelegramm.ui.activity.createGroup

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.visola_khudoynazarova.chattelegramm.R
import com.visola_khudoynazarova.chattelegramm.databinding.CreateGroupRvItemsBinding
import com.visola_khudoynazarova.chattelegramm.model.Users

class CreateGroupAdapter(private val list: ArrayList<Users>) :
    RecyclerView.Adapter<CreateGroupAdapter.VH>() {
    private val TAG="CreateGroupAdapter"

    val checkList = arrayListOf<Users>()

    init {
        list.sortBy { it.name }
        notifyDataSetChanged()
    }

    inner class VH(private val binding: CreateGroupRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        fun onBind(users: Users) {
            binding.users = users
            binding.executePendingBindings()
            checkBox.setOnClickListener {
                if (checkBox.isChecked) {
                    checkList.add(list[adapterPosition])
                    Log.d(TAG, "onBind:  ${list[adapterPosition].name}")
                } else {
                    checkList.removeAll { it.number == list[adapterPosition].number }
                    Log.d("TT", "remove ${list[adapterPosition].name} ")
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding: CreateGroupRvItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.create_group_rv_items,
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.onBind(list[position])

    override fun getItemCount(): Int = list.size
    fun getCheckedList() = checkList
}