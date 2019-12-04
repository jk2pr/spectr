package com.jk.spectr.network

import com.jk.spectr.data.Country
import io.reactivex.Observable
import retrofit2.http.GET

interface IApi {

    @GET("api/json/get/Vk-LhK44U")
    fun getCountryList(): Observable<List<Country>>
}