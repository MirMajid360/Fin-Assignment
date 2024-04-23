package com.majid.mvvmtemplate.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.majid.mvvmtemplate.databinding.ItemUserBinding
import com.majid.mvvmtemplate.models.User
import com.majid.mvvmtemplate.utils.IListeners

class UserAdapter(
    var context: Context,
    var list: ArrayList<User>,
    var listener: IListeners.IClickListeners,
) : RecyclerView.Adapter<UserAdapter.ChatsViewHolder>() {
    var searchText = ""

    inner class ChatsViewHolder(val binding: ItemUserBinding) : ViewHolder(binding.root) {
        fun bind(model: User,position: Int) {


            with(model) {
                binding.apply {
                    tvNameValue.text = name
                    tvAgeValue.text = age.toString()
                    tvCityValue.text = city
                }

            }

            binding.root.setOnClickListener {
                listener.onItemClicked(model,position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val itemBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ChatsViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {

        try {


            holder.bind(list[position],position)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(listTemp: ArrayList<User>, text: String) {

        try {


            list = listTemp
            this.searchText = text
            notifyDataSetChanged()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun sortByName(){
        list.sortBy { it.name }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortByAge(){
        list.sortBy { it.age }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortByCity(){
        list.sortBy { it.city }
        notifyDataSetChanged()
    }

    fun updateList(listTemp: ArrayList<User>?) {
        list.clear()
        if (listTemp != null) {
            list.addAll(listTemp)
        }
        notifyDataSetChanged()

    }
}