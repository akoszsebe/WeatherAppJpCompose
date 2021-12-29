package com.personal.weathercompose.ui.feature.location_weather


import com.personal.weathercompose.base.ViewEvent
import com.personal.weathercompose.base.ViewSideEffect
import com.personal.weathercompose.base.ViewState
import com.personal.weathercompose.model.LocationWithWeather

class LocationWeatherContract {
    sealed class Event : ViewEvent {
        data class CategorySelection(val categoryName: String) : Event()
    }

    data class State(val locationWithWeather: LocationWithWeather = LocationWithWeather(), val isLoading: Boolean = false) : ViewState

    sealed class Effect : ViewSideEffect {
        object DataWasLoaded : Effect()

        sealed class Navigation : Effect() {
            data class ToCategoryDetails(val locationName: String) : Navigation()
        }
    }

}