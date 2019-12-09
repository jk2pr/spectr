package com.jk.spectr.ui.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jk.spectr.R
import com.jk.spectr.callbacks.OnItemClickListner
import com.jk.spectr.data.Country
import com.jk.spectr.databinding.MemberListItemBinding
import java.util.*


class MemberAdapter(var items: ArrayList<Country.Members>) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>(),
    OnItemClickListner {


    lateinit var context: Context


    override fun onItemClick(view: View, item: Country) {


        val data = Bundle()
        val members = item.members
        data.putParcelableArrayList("MEMBERS", members)


        view.findNavController().navigate(R.id.action_mainFragment_to_memberFragment, data)

        //  println(Toast.makeText(context, "CLicked", Toast.LENGTH_SHORT).show())


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val binding = DataBindingUtil
            .inflate<MemberListItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.member_list_item,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setFilter(newList: List<Country.Members>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: MemberListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plist: Country.Members) {


            with(binding) {
                member = plist
                executePendingBindings()
               // tvMemberAge.text=plist.age.toString()
             //   tvMemberName.text=plist.name.first.plus(" ").plus(plist.name.second)
             //   tvMemberPhone.text=plist.phone


            }

        }

    }
}

