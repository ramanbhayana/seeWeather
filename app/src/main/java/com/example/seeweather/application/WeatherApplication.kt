package com.example.seeweather.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.example.seeweather.dagger.modules.ApplicationModule
import com.example.seeweather.api.network.InternetAvailabilityChecker
import com.example.seeweather.api.network.InternetConnectivityListener
import com.example.seeweather.commonUtils.common.LoadingDialog
import com.example.seeweather.dagger.components.ApplicationComponent
import com.example.seeweather.dagger.components.DaggerApplicationComponent


class WeatherApplication : MultiDexApplication(), Application.ActivityLifecycleCallbacks,
    InternetConnectivityListener {

    private var isConnected: Boolean = false
    private var mInternetAvailabilityChecker: InternetAvailabilityChecker? = null
    lateinit var applicationComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()
        InternetAvailabilityChecker.init(this)
        registerActivityLifecycleCallbacks(this)

        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)

    }


    override fun onInternetConnectivityChanged(isConnected: Boolean) {
        this.isConnected = isConnected
    }

    override fun onActivityPaused(activity: Activity) {
        mInternetAvailabilityChecker?.removeInternetConnectivityChangeListener(this)
        LoadingDialog.unbind()
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityResumed(activity: Activity) {
        mInternetAvailabilityChecker = InternetAvailabilityChecker.instance
        mInternetAvailabilityChecker?.addInternetConnectivityListener(this)
        LoadingDialog.bindWith(activity)
    }

}