package com.example.seeweather.dagger.components

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.seeweather.dagger.ApplicationContext
import com.example.seeweather.dagger.modules.ApplicationModule
import com.example.seeweather.api.network.NetworkHelper
import com.example.seeweather.api.network.NetworkService
import com.example.seeweather.application.WeatherApplication
import com.example.seeweather.commonUtils.rx.SchedulerProvider
import com.example.seeweather.db.WeatherDataRepository
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: WeatherApplication)

    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getNetworkHelper(): NetworkHelper

    fun getSchedulerProvider(): SchedulerProvider

    fun getCompositeDisposable(): CompositeDisposable

    fun getAlertDialogBuilder(): AlertDialog.Builder

    fun getLayoutInflater(): LayoutInflater

    fun getweatherDataRepository(): WeatherDataRepository?

}