package com.jk.spectr.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jk.spectr.SpectrApplication
import com.jk.spectr.data.Country
import com.jk.spectr.network.IApi
import com.jk.spectr.ui.adapter.CountryAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var data: ArrayList<Country> = ArrayList()
    var adapter = CountryAdapter(data)
    val liveData: MutableLiveData<List<Country>> =
        MutableLiveData()


    @Inject
    lateinit var iApi: IApi

    init {

        SpectrApplication.appComponent.inject(this)
        val subscribe = iApi.getCountryList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { liveData.value = it }
    }


}
