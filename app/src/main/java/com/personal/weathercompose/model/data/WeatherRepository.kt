package com.personal.weathercompose.model.data


import com.personal.weathercompose.model.LocationWithWeather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

    private var cachedLocationWithWeather: LocationWithWeather? = null


    suspend fun getLocationWithWeather(latitude: Double, longitude: Double): LocationWithWeather {
        var cachedLocationWithWeather = cachedLocationWithWeather
        if (cachedLocationWithWeather == null) {
            cachedLocationWithWeather = weatherApi.LocationWithWeather(latitude,longitude)
            this.cachedLocationWithWeather = cachedLocationWithWeather
        }
        return cachedLocationWithWeather
    }

}