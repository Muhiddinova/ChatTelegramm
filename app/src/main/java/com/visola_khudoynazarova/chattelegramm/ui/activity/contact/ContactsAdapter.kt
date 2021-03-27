package com.visola_khudoynazarova.chattelegramm.ui.activity.contact

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.visola_khudoynazarova.chattelegramm.R
import com.visola_khudoynazarova.chattelegramm.databinding.ContactsRvItemsBinding
import com.visola_khudoynazarova.chattelegramm.model.Users

class ContactsAdapter(private val contactsViewModel: ContactsViewModel) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {


    private val TAG = "ContactsAdapter"

    private val contactList: ArrayList<Users> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding:ContactsRvItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.contacts_rv_items,
            parent,
            false
        )
        return ContactsViewHolder(binding)
    }

    override fun getItemCount(): Int = contactList.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) = holder.bind(contactList[position])

    inner class ContactsViewHolder(private val binding: ContactsRvItemsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(user: Users){
            binding.user= user
            binding.contactsVM = contactsViewModel
            binding.executePendingBindings()
        }
    }

    fun addContacts(list: ArrayList<Users>){
        Log.d(TAG, "addContacts: called")
        contactList.clear()
        list.sortBy {it.name}
        contactList.addAll(list.distinct())
        notifyDataSetChanged()
    }
}