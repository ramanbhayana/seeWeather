package com.example.seeweather.dagger.modules

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.seeweather.db.WeatherDataRepository
import com.example.seeweather.dagger.ApplicationContext
import com.example.seeweather.BuildConfig
import com.example.seeweather.api.network.NetworkHelper
import com.example.seeweather.api.network.NetworkService
import com.example.seeweather.api.network.Networking
import com.example.seeweather.application.WeatherApplication
import com.example.seeweather.commonUtils.rx.RxSchedulerProvider
import com.example.seeweather.commonUtils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: WeatherApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application

    /**
     * Since this function do not have @Singleton then each time CompositeDisposable is injected
     * then a new instance of CompositeDisposable will be provided
     */

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    /**
     * We need to write @Singleton on the provide method if we are create the instance inside this method
     * to make it singleton. Even if we have written @Singleton on the instance's class
     */
    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService =
        Networking.create(
            "",
            BuildConfig.Base_Url,
            application.cacheDir,
            10 * 1024 * 1024, // 10MB
            application
        )

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

    @Provides
    fun provideAlertDialogBuilder(): AlertDialog.Builder = AlertDialog.Builder(application)

    @Provides
    fun provideLayoutInflater(): LayoutInflater = LayoutInflater.from(application)

    @Provides
    fun provideWeatherDataRepository(): WeatherDataRepository? =
        WeatherDataRepository.getInstance(application)

}