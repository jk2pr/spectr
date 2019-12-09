package com.jk.spectr.callbacks

import android.view.View
import com.jk.spectr.data.Country

interface FragmentCallBack {
    fun onFav()
    fun onFollow()
    fun onItemClick(view: View, item: Country)
    fun invalidateRequest(isLongPress: Boolean, country: Country)

}