package com.personal.weathercompose.model

import com.google.gson.annotations.SerializedName

data class LocationWithWeather(
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("name")
    var name: String = "empty",
    @SerializedName("main")
    var main: MainInfo = MainInfo(),
    @SerializedName("weather")
    var weather: List<Weather> = listOf(),
)

data class MainInfo(
    @SerializedName("temp")
    var temp: Double? = 0.0,
    @SerializedName("feels_like")
    var feelsLike: Double? = 0.0,
    @SerializedName("temp_min")
    var tempMin: Double? = 0.0,
    @SerializedName("temp_max")
    var tempMax: Double? = 0.0,
    @SerializedName("pressure")
    var pressure: Long? = 0,
    @SerializedName("humidity")
    var humidity: Long? = 0,
)

data class Weather(
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("main")
    var main: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("icon")
    var icon: String = ""
)