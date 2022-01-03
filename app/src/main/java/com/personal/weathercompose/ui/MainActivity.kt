package com.personal.weathercompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.personal.weathercompose.ui.screen.Screen
import com.personal.weathercompose.ui.screen.dashboard.DashboardScreen
import com.personal.weathercompose.ui.theme.WeatherComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            val systemUiController = rememberSystemUiController()
            ProvideWindowInsets {

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        Color.Transparent,
                        darkIcons = false
                    )
                }
                WeatherComposeTheme {

                    Surface {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Dashboard.route
                        ) {

                            // Dashboard
                            composable(Screen.Dashboard.route) {
                                println("open dashboard")
                                DashboardScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}