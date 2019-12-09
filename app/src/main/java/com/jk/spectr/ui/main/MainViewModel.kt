package com.jk.spectr.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jk.spectr.SpectrApplication
import com.jk.spectr.data.Country
import com.jk.spectr.network.IApi
import com.jk.spectr.ui.adapter.CountryAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainViewModel(fragment: CountryListFragment) : ViewModel() {
    // TODO: Implement the ViewModel
    private var data: ArrayList<Country> = ArrayList()
    var adapter = CountryAdapter(fragment,data)
    val liveData: MutableLiveData<List<Country>> =
        MutableLiveData()


    @Inject
    lateinit var iApi: IApi
    private var subscriptions = CompositeDisposable()

    init {

        SpectrApplication.appComponent.inject(this)

         subscriptions.add( iApi.getCountryList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { liveData.value = it }
         )
    }


}

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(param: CountryListFragment) :
    ViewModelProvider.Factory {
    private val mParam: CountryListFragment = param
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(mParam) as T
    }

}
