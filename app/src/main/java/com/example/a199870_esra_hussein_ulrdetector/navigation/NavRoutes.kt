package com.example.a199870_esra_hussein_ulrdetector.navigation

sealed class Screen(val route: String){
    object Welcome : Screen("welcome")
    object Home: Screen("home")
    object Result: Screen("result/{url}")
    object History: Screen("history")

    object QrScanner : Screen("qr_scanner")

    object Login : Screen("login")

    object SignUp: Screen("signup")
}