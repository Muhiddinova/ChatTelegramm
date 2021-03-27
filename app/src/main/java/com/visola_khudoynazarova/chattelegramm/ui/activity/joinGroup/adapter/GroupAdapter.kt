package com.visola_khudoynazarova.chattelegramm.ui.activity.joinGroup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.visola_khudoynazarova.chattelegramm.model.Group
import com.visola_khudoynazarova.chattelegramm.databinding.GroupsItemBinding

class GroupAdapter(private val itemClickListener: ((Group) -> Unit)) :
    RecyclerView.Adapter<GroupAdapter.VH>() {

    private var list = listOf<Group>()

    fun updateData(list: List<Group>){
        this.list = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupAdapter.VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GroupsItemBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.invoke(list[position])
        }
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

    class VH(private val binding: GroupsItemBinding) : RecyclerView.ViewHolder(binding.root) {

       fun onBind(group:Group){
           binding.group=group
       }
    }
}