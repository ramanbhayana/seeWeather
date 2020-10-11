package com.example.seeweather.repository


import com.example.seeweather.BuildConfig
import com.example.seeweather.api.network.NetworkService
import com.example.seeweather.dataclasses.WeatherDataClass
import com.example.seeweather.db.WeatherDataRepository
import com.example.seeweather.db.entity.WeatherEntity
import io.reactivex.Single
import javax.inject.Inject

class CentralRepository @Inject constructor(
    val networkService: NetworkService,
    val WeatherDataRepository: WeatherDataRepository?
) {

    fun getWeatherByCity(cityName: String): Single<WeatherDataClass> = networkService.getWeatherByCity(cityName,
        BuildConfig.openweathermapapikey)

    fun insertWeatherEntityIntoDatabase(weatherEntity: WeatherEntity) {
        WeatherDataRepository?.insertWeatherEntity(weatherEntity)
    }

    fun getWeatherEntityFromDatabase(cityName: String): WeatherEntity? = WeatherDataRepository?.getWeatherEntity(cityName)
}