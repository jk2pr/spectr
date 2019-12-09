package com.jk.spectr.ui.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jk.spectr.R
import com.jk.spectr.callbacks.FragmentCallBack
import com.jk.spectr.callbacks.OnItemClickListner
import com.jk.spectr.data.Country
import com.jk.spectr.databinding.CountryListItemBinding
import kotlinx.android.synthetic.main.country_list_item.view.*
import java.util.*


class CountryAdapter(
    private var fragmentCallBack: FragmentCallBack,
    val items: ArrayList<Country>
) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>(),
    OnItemClickListner, View.OnLongClickListener {

    lateinit var context: Context


    override fun onItemClick(view: View, item: Country) {


        fragmentCallBack.onItemClick(view, item)

        //  println(Toast.makeText(context, "CLicked", Toast.LENGTH_SHORT).show())


    }

    fun getSelectedItems(): List<Country> {

        return items.filter {
            it.isSelected
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val binding = DataBindingUtil
            .inflate<CountryListItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.country_list_item,
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

    inner class ViewHolder(private val binding: CountryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plist: Country) {

            with(binding) {
                item = plist
                itemClickListener = this@CountryAdapter
                itemView.tag = item
                itemView.setOnLongClickListener(this@CountryAdapter)
                if (item!!.isSelected) {
                    itemView.setBackgroundColor(
                        getColor(
                            context,
                            android.R.color.darker_gray
                        )
                    )
                } else
                    itemView.setBackgroundColor(
                        getColor(
                            context,
                            android.R.color.white
                        )
                    )


                if (item!!.isFav)
                    itemView.tv_company_name.setDrawableColor(R.color.colorPrimary)
                else
                    itemView.tv_company_name.setDrawableColor(android.R.color.transparent)


                val drawable: Drawable?
                if (item!!.isFollowing)
                    drawable = getDrawable(context, R.drawable.ic_person_add_24px)
                else
                    drawable = null
                //  itemView.tv_company_website.setDrawableStart(null)

                itemView.tv_company_website.setDrawableStart(drawable)
                // itemLongClickListener = this@CountryAdapter
                executePendingBindings()

            }

        }

    }

    fun TextView.setDrawableColor(@ColorRes color: Int) {
        compoundDrawables.filterNotNull().forEach {
            it.colorFilter = PorterDuffColorFilter(getColor(context, color), PorterDuff.Mode.SRC_IN)
        }
    }

    fun TextView.setDrawableStart(drawable: Drawable?) {


        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        compoundDrawables.filterNotNull().forEach {
            //it.colorFilter = PorterDuffColorFilter(getColor(context, color), PorterDuff.Mode.SRC_IN)
        }
    }

    companion object {

        @JvmStatic
        @BindingAdapter("profileImage")
        fun loadImage(view: ImageView, url: String) {
            Glide
                .with(view.context)
                .load(url)
                .centerCrop()
                //  .placeholder(R.drawable.loading_spinner)
                .into(view)
        }

    }


    override fun onLongClick(p0: View): Boolean {
        Toast.makeText(p0.context, "Long press", Toast.LENGTH_SHORT).show()
        fragmentCallBack.invalidateRequest(true, p0.tag as Country)
        return true
    }

/*
    @BindingAdapter("onLongClick")
    override fun onItemLongPress(view: View, item: Country) {

    }*/
}

