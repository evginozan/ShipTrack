package com.evginozan.kargotakip.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object CargoDetail : Screen("cargo_detail/{trackingCode}") {
        fun createRoute(trackingCode: String): String = "cargo_detail/$trackingCode"
    }
    object Profile : Screen("profile")
    object DeliveryCode : Screen("delivery_code/{trackingCode}") {
        fun createRoute(trackingCode: String): String = "delivery_code/$trackingCode"
    }
}