package com.jk.spectr.callbacks

import android.view.View
import com.jk.spectr.data.Country

interface OnItemClickListner {

    fun onItemClick(view: View, item: Country)
}