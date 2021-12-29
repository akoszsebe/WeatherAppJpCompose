package com.personal.weathercompose.ui.feature.entry


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.personal.weathercompose.ui.feature.location_weather.LocationWeatherContract
import com.personal.weathercompose.ui.feature.location_weather.LocationWeatherScreen
import com.personal.weathercompose.ui.feature.location_weather.LocationWeatherViewModel
import com.personal.weathercompose.ui.theme.WeatherComposeTheme
import dagger.hilt.android.AndroidEntryPoint


// Single Activity per app
@AndroidEntryPoint
class EntryPointActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                WeatherComposeTheme {
                    FoodApp()
                }
            }
        }
    }

}

@Composable
private fun FoodApp() {
    ProvideWindowInsets {
        val systemUiController = rememberSystemUiController()

        SideEffect {
            systemUiController.setSystemBarsColor(
                Color.Transparent,
                darkIcons = false
            )
        }

        val navController = rememberNavController()
        NavHost(navController, startDestination = NavigationKeys.Route.LOCATION_WEATHER_DEST) {
            composable(route = NavigationKeys.Route.LOCATION_WEATHER_DEST) {
                LocationWeatherDestination(navController)
            }
        }

    }
}

@Composable
private fun LocationWeatherDestination(navController: NavHostController) {
    val viewModel: LocationWeatherViewModel = hiltViewModel()
    val state = viewModel.viewState.value
    LocationWeatherScreen(
        state = state,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is LocationWeatherContract.Effect.Navigation.ToCategoryDetails) {
                navController.navigate("${NavigationKeys.Route.LOCATION_WEATHER_DEST}/${navigationEffect.locationName}")
            }
        })
}

object NavigationKeys {

    object Arg {
        const val FOOD_CATEGORY_ID = "foodCategoryName"
    }

    object Route {
        const val LOCATION_WEATHER_DEST = "location_weather_dest"
    }

}