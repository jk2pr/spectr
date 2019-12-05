package com.jk.spectr.ui.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jk.spectr.R
import com.jk.spectr.callbacks.OnItemClickListner
import com.jk.spectr.data.Country
import com.jk.spectr.databinding.ListItemBinding
import java.util.*


class CountryAdapter(val items: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>(),
    OnItemClickListner {


    lateinit var context: Context

    override fun onItemClick(view: View, item: Country) {


        val data = Bundle()
        //   data.putSerializable("LAT_LONG", item.coordinate)
        //    view.findNavController().navigate(R.id.action_listFragment_to_mapFragment, data)

        //  println(Toast.makeText(context, "CLicked", Toast.LENGTH_SHORT).show())


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val binding = DataBindingUtil
            .inflate<ListItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.list_item,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size

    }

    fun addItems(found: List<Country>) {
        items.addAll(found)
        notifyItemRangeInserted(0, found.size - 1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setFilter(newList: List<Country>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plist: Country) {

            with(binding) {
                item = plist

                Glide
                    .with(root)
                    .load(plist.logo)
                    .centerCrop()
                    //  .placeholder(R.drawable.loading_spinner)
                    .into(imgCompanyLogo);


                //Glide.with(binding.root).load(plist.logo).into(imgCompanyLogo);
                itemClickListener = this@CountryAdapter
                tvCompanyName.text = plist.company
                tvCompanyDescription.text = plist.about
                tvCompanyWebsite.text = plist.website


            }

        }

    }
}

