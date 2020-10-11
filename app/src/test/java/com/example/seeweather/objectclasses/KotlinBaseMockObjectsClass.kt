package com.example.seeweather.objectclasses


import com.example.seeweather.api.network.NetworkHelper
import com.example.seeweather.api.network.NetworkService
import com.example.seeweather.application.WeatherApplication
import com.example.seeweather.commonUtils.rx.SchedulerProvider
import com.example.seeweather.db.WeatherDataRepository
import com.example.seeweather.db.dao.WeatherDao
import com.example.seeweather.utils.TestSchedulerProvider
import com.example.seeweather.utils.mock
import io.reactivex.disposables.CompositeDisposable

open class KotlinBaseMockObjectsClass {
    val mockSchedulerProvider = mock<SchedulerProvider>()
    val testSchedulerProvider = TestSchedulerProvider()
    val mockCompositeDisposable = mock<CompositeDisposable>()
    val mockNetworkService = mock<NetworkService>()
    val mockApplication = mock<WeatherApplication>()
    val mockNetworkHelper = mock<NetworkHelper>()
    val mockDataRepository = mock<WeatherDataRepository>()
    val mockDao = mock<WeatherDao>()
}