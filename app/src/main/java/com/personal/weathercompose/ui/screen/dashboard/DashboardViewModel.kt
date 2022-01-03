package com.personal.weathercompose.ui.screen.dashboard


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.weathercompose.model.LocationWithWeather
import com.personal.weathercompose.model.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    val locationWithWeather = MutableStateFlow(LocationWithWeather())

    init {
        viewModelScope.launch { getWeather() }
    }

    private suspend fun getWeather() {
        locationWithWeather.value = repository.getLocationWithWeather()

    }

}