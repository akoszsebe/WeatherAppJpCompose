package com.personal.weathercompose.ui.feature.location_weather


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.systemBarsPadding
import com.personal.weathercompose.R
import com.personal.weathercompose.base.LAUNCH_LISTEN_FOR_EFFECTS
import com.personal.weathercompose.ui.theme.BlueText
import com.personal.weathercompose.ui.theme.WeatherComposeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun LocationWeatherScreen(
    state: LocationWeatherContract.State,
    effectFlow: Flow<LocationWeatherContract.Effect>?,
    onEventSent: (event: LocationWeatherContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: LocationWeatherContract.Effect.Navigation) -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    // Listen for side effects from the VM
    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is LocationWeatherContract.Effect.Navigation.ToCategoryDetails -> onNavigationRequested(
                    effect
                )
            }
        }?.collect()
    }
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.winter),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        Scaffold(
            backgroundColor = Color.Transparent,
            scaffoldState = scaffoldState,
            modifier = Modifier.systemBarsPadding()
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.BottomStart)
                        .padding(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = state.locationWithWeather.name,
                        fontSize = 36.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W500
                    )
                    Row {
                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = state.locationWithWeather.main.temp.toString(),
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
                        text = "Feels like : " + state.locationWithWeather.main.feelsLike + "°",
                        fontSize = 36.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W500
                    )
                }
                if (state.isLoading)
                    LoadingBar()
            }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherComposeTheme {
        LocationWeatherScreen(LocationWeatherContract.State(), null, { }, { })
    }
}