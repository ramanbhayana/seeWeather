package com.example.seeweather.mainactivitytest


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.seeweather.objectclasses.KotlinBaseMockObjectsClass
import com.example.seeweather.utils.mock
import com.example.seeweather.utils.whenever
import com.example.seeweather.BuildConfig
import com.example.seeweather.dataclasses.WeatherDataClass
import com.example.seeweather.repository.CentralRepository
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

class MainActivityRepositoryTest : KotlinBaseMockObjectsClass() {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val mockMainActivityRepositoryTest = mock<CentralRepository>()
    val centralRepository by lazy {
        CentralRepository(mockNetworkService, mockDataRepository)
    }

    @Before
    fun initTest() {
        Mockito.reset(mockNetworkService, mockDataRepository, mockApplication)
    }

    @Test
    fun verifyConstructorParameters() {
        assertEquals(mockNetworkService, centralRepository.networkService)
        assertEquals(mockDataRepository, centralRepository.WeatherDataRepository)
    }

    @Test
    fun verifyGetWeatherByCityName() {
        whenever(mockNetworkService.getWeatherByCity("Delhi", BuildConfig.openweathermapapikey))
            .thenReturn(Single.just(WeatherDataClass()))
        whenever(mockMainActivityRepositoryTest.getWeatherByCity(ArgumentMatchers.anyString()))
            .thenReturn(Single.just(WeatherDataClass()))
        centralRepository.getWeatherByCity("Delhi").test().assertComplete()
    }
}