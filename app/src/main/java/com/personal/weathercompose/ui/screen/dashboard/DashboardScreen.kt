package com.personal.weathercompose.ui.screen.dashboard


import android.Manifest
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.systemBarsPadding
import com.personal.weathercompose.R
import com.personal.weathercompose.model.LocationWithWeather
import com.personal.weathercompose.ui.composable.PermissionAction
import com.personal.weathercompose.ui.composable.PermissionUI
import com.personal.weathercompose.ui.theme.BlueGrey800

private const val TAG = "PermissionTestUI"

@ExperimentalCoilApi
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    val context = LocalContext.current
    val performLocationAction by viewModel.performLocationAction.collectAsState()
    val locationDone by viewModel.locationDone.collectAsState()
    val locationWithWeather by viewModel.locationWithWeather.collectAsState()
    if (performLocationAction == false) {
        Log.d(TAG, "Invoking Permission UI")
        PermissionUI(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
            stringResource(id = R.string.permission_location_rationale),
            scaffoldState
        ) { permissionAction ->
            when (permissionAction) {
                is PermissionAction.OnPermissionGranted -> {
                    viewModel.setPerformLocationAction(true)
                    //Todo: do something now as we have location permission
                    Log.d(TAG, "Location has been granted")
                }
                is PermissionAction.OnPermissionDenied -> {
                    viewModel.setPerformLocationAction(false)
                }
            }
        }
    } else {
        viewModel.getLocation(context)
        if (locationDone){
            viewModel.getWeather()
            WeatherComponent(scaffoldState, locationWithWeather)
        } else {
            LoadingBar()
        }

    }
}

@Composable
private fun WeatherComponent(
    scaffoldState: ScaffoldState,
    locationWithWeather: LocationWithWeather
) {
    Scaffold(
        backgroundColor = BlueGrey800,
        scaffoldState = scaffoldState,
    ) {
        Box(modifier = Modifier.systemBarsPadding()) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (locationWithWeather.weather.isNotEmpty()) {
                    Box(modifier = Modifier.weight(1f)) {
                        Image(
                            painter = rememberImagePainter("https://openweathermap.org/img/wn/${locationWithWeather.weather.first().icon}@4x.png"),
                            contentDescription = null,
                            modifier = Modifier
                                .size(228.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = locationWithWeather.name,
                    fontSize = 36.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W500
                )
                Row {
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text =  String.format("%.1f", locationWithWeather.main.temp),
                        fontSize = 140.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "°",
                        fontSize = 80.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = "Feels like : " + String.format("%.1f", locationWithWeather.main.feelsLike) + "°",
                    fontSize = 36.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W500
                )
            }
            if (locationWithWeather.id == 0L)
                LoadingBar()
        }
    }
}

@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}