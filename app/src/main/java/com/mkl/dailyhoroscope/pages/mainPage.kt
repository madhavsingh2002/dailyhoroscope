package com.mkl.dailyhoroscope.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("splash") { SplashScreen(navController) }
            composable("home") { HomePage(navController) }
            composable("getstarted") {GetStartedScreen(navController)}
            composable("finalPage") {FinalPage(navController)}
            composable("HoroscopeDataScreen/{horoscopesign}/{dateRange}"){ backStackEntry ->
                val horoscopeSign = backStackEntry.arguments?.getString("horoscopesign")
                val dateRange = backStackEntry.arguments?.getString("dateRange")
                if (horoscopeSign != null) {
                    HoroscopeDataScreen(horoscopeSign,dateRange)
                } else {
                    Text(text = "Invalid horoscopesign")
                }
            }
        }
    }
}

