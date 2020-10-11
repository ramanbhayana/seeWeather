package com.example.seeweather.dagger.modules

import androidx.lifecycle.ViewModelProviders
import com.example.seeweather.mvvm.BaseActivity
import com.example.seeweather.mvvm.ViewModelProviderFactory
import com.example.seeweather.api.network.NetworkHelper
import com.example.seeweather.commonUtils.rx.SchedulerProvider
import com.example.seeweather.repository.CentralRepository
import com.example.seeweather.viewModel.MainActivityViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>){
    @Provides
    fun provideMainActivityViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper, centralRepository: CentralRepository
    ): MainActivityViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(MainActivityViewModel::class) {
            MainActivityViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper,
                centralRepository
            )
            //this lambda creates and return MainActivityViewModel
        }).get(MainActivityViewModel::class.java)
}