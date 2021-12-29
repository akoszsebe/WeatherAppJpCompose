package com.personal.weathercompose.ui.feature.location_weather


import androidx.lifecycle.viewModelScope
import com.personal.weathercompose.base.BaseViewModel
import com.personal.weathercompose.model.LocationWithWeather
import com.personal.weathercompose.model.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationWeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    BaseViewModel<LocationWeatherContract.Event, LocationWeatherContract.State, LocationWeatherContract.Effect>() {

    init {
        viewModelScope.launch { getFoodCategories() }
    }

    override fun setInitialState() =
        LocationWeatherContract.State(locationWithWeather = LocationWithWeather(), isLoading = true)

    override fun handleEvents(event: LocationWeatherContract.Event) {
        when (event) {
            is LocationWeatherContract.Event.CategorySelection -> {
                setEffect { LocationWeatherContract.Effect.Navigation.ToCategoryDetails(event.categoryName) }
            }
        }
    }

    private suspend fun getFoodCategories() {
        val locationWithWeather = repository.getLocationWithWeather()
        setState {
            copy(locationWithWeather = locationWithWeather, isLoading = false)
        }
        setEffect { LocationWeatherContract.Effect.DataWasLoaded }
    }

}