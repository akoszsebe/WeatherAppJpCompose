package com.personal.weathercompose.model.data

import com.personal.weathercompose.BuildConfig
import com.personal.weathercompose.model.LocationWithWeather
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherApi @Inject constructor(private val service: Service) {

    suspend fun LocationWithWeather(latitude: Double, longitude: Double): LocationWithWeather = service.getLocationWithWeather(latitude, longitude)

    interface Service {

        @GET("weather")
        suspend fun getLocationWithWeather(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("appid") appId: String = BuildConfig.OPENWEATHERMAP_KEY,
            @Query("units") units: String = "metric"
        ): LocationWithWeather

    }

    companion object {
        const val API_URL = "https://api.openweathermap.org/data/2.5/"
    }
}
