package com.example.a199870_esra_hussein_ulrdetector.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a199870_esra_hussein_ulrdetector.UrlViewModel
import com.example.a199870_esra_hussein_ulrdetector.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(navController: NavController, viewModel: UrlViewModel) {

    val result = viewModel.currentPrediction
    val lastUrl = viewModel.lastScannedUrl
    val reasons = viewModel.lastReasons

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("URL Result") },
                navigationIcon = {
                    TextButton(onClick = { navController.navigate(Screen.Home.route) }) {
                        Text("Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("Scanned URL:", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = lastUrl,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(25.dp))

            Text(
                text = "Status: $result",
                style = MaterialTheme.typography.headlineSmall,
                color = if (result.contains("SAFE")) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )

            Spacer(Modifier.height(20.dp))

            if (reasons.isNotEmpty() && reasons.first() != "Failed to analyze URL") {
                Text("Why?", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))

                reasons.forEach { reason ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("- $reason", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            Button(
                onClick = { navController.navigate(Screen.Home.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Scan Another URL")
            }
        }
    }
}
