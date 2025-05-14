package com.evginozan.kargotakip.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.evginozan.kargotakip.presentation.auth.LoginScreen
import com.evginozan.kargotakip.presentation.cargo.CargoDetailScreen
import com.evginozan.kargotakip.presentation.cargo.DeliveryCodeScreen
import com.evginozan.kargotakip.presentation.home.HomeScreen
import com.evginozan.kargotakip.presentation.profile.ProfileScreen
import com.evginozan.kargotakip.presentation.splash.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }


        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.CargoDetail.route,
            arguments = listOf(navArgument("trackingCode") { type = NavType.StringType })
        ) { backStackEntry ->
            val trackingCode = backStackEntry.arguments?.getString("trackingCode") ?: ""
            CargoDetailScreen(trackingCode = trackingCode, navController = navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(
            route = Screen.DeliveryCode.route,
            arguments = listOf(navArgument("trackingCode") { type = NavType.StringType })
        ) { backStackEntry ->
            val trackingCode = backStackEntry.arguments?.getString("trackingCode") ?: ""
            DeliveryCodeScreen(trackingCode = trackingCode, navController = navController)
        }
    }
}