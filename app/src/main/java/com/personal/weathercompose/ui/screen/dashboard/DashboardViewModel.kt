package com.personal.weathercompose.ui.screen.dashboard


import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.personal.weathercompose.model.LocationWithWeather
import com.personal.weathercompose.model.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    val locationWithWeather = MutableStateFlow(LocationWithWeather())

    private val _performLocationAction: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val performLocationAction = _performLocationAction.asStateFlow()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val location = MutableStateFlow(Location(LocationManager.NETWORK_PROVIDER))

    val _locationDone: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val locationDone = _locationDone.asStateFlow()

    fun getWeather() {
        viewModelScope.launch {
            locationWithWeather.value = repository.getLocationWithWeather(location.value.latitude,location.value.longitude)
        }
    }

    fun setPerformLocationAction(request: Boolean) {
        _performLocationAction.value = request
    }

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (_performLocationAction.value) {
            fusedLocationClient.lastLocation
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result != null) {
                            location.value = task.result
                            _locationDone.value = true
                        }
                    } else {
                        setPerformLocationAction(false)
                    }
                }
        }
    }

}