package com.personal.weathercompose.ui.screen.dashboard


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.systemBarsPadding
import com.personal.weathercompose.R


@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {

    val locationWithWeather by viewModel.locationWithWeather.collectAsState()

    val scaffoldState: ScaffoldState = rememberScaffoldState()
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
                        text = locationWithWeather.name,
                        fontSize = 36.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W500
                    )
                    Row {
                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = locationWithWeather.main.temp.toString(),
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
                        text = "Feels like : " + locationWithWeather.main.feelsLike + "°",
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
