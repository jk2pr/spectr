package com.jk.spectr.callbacks

import android.view.View
import com.jk.spectr.data.Country
import kotlinx.android.parcel.RawValue


interface OnItemClickListner {
    fun onItemClick(view: View, item: Country)
}
