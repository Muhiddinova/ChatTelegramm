package com.visola_khudoynazarova.chattelegramm.ui.activity.conversation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.visola_khudoynazarova.chattelegramm.model.Group
import com.visola_khudoynazarova.chattelegramm.databinding.RecieveMessageItemBinding
import com.visola_khudoynazarova.chattelegramm.databinding.SentMessageRvItemBinding

class MessageAdapter() :RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class VH1(private val binding:RecieveMessageItemBinding):RecyclerView.ViewHolder(binding.root){

        fun onBind(group: Group){

        }

    }

    class VH2(private val binding: SentMessageRvItemBinding):RecyclerView.ViewHolder(binding.root){

        fun onBind(group: Group){

        }

    }
}