package com.example.seeweather.api.network

import com.example.seeweather.dataclasses.WeatherDataClass
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {
    @GET("weather")
    fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("appid") openWeatherKey: String
    ): Single<WeatherDataClass>
}