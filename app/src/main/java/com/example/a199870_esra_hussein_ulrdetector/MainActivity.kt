package com.example.a199870_esra_hussein_ulrdetector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a199870_esra_hussein_ulrdetector.navigation.Screen
import com.example.a199870_esra_hussein_ulrdetector.ui.HistoryScreen
import com.example.a199870_esra_hussein_ulrdetector.ui.HomeScreen
import com.example.a199870_esra_hussein_ulrdetector.ui.QrScannerScreen
import com.example.a199870_esra_hussein_ulrdetector.ui.ResultScreen
import com.example.a199870_esra_hussein_ulrdetector.ui.WelcomeScreen
import com.example.a199870_esra_hussein_ulrdetector.ui.theme.A199870_Esra_Hussein_ULRDetectorTheme
import com.google.firebase.auth.FirebaseAuth
import com.example.a199870_esra_hussein_ulrdetector.ui.LoginScreen
import com.example.a199870_esra_hussein_ulrdetector.ui.SignUpScreen

class MainActivity : ComponentActivity() {

    private val viewModel: UrlViewModel by viewModels {
        UrlViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A199870_Esra_Hussein_ULRDetectorTheme(dynamicColor = false) {

                val navController = rememberNavController()

                val auth = FirebaseAuth.getInstance()
                val startDestination =
                    if (auth.currentUser != null) Screen.Home.route
                    else Screen.Welcome.route

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                )
                {
                    composable(Screen.Welcome.route) {
                        WelcomeScreen(navController)
                    }
                    composable(Screen.Home.route) {
                        HomeScreen(navController, viewModel)
                    }
                    composable(Screen.Result.route) {
                        ResultScreen(navController, viewModel)
                    }
                    composable(Screen.History.route) {
                        HistoryScreen(navController, viewModel)
                    }
                    composable(Screen.QrScanner.route) {
                        QrScannerScreen(navController, viewModel)
                    }
                    composable(Screen.Login.route) {
                        LoginScreen(navController)
                    }
                    composable(Screen.SignUp.route) {
                        SignUpScreen(navController)
                    }
                }
            }
        }
    }
}
