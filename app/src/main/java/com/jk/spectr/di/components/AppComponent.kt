package com.jk.spectr.di.components

import com.jk.spectr.di.modules.NetworkModule
import com.jk.spectr.ui.main.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface AppComponent {
    fun inject(auth: MainViewModel)
    fun inject(net: NetworkModule)

}