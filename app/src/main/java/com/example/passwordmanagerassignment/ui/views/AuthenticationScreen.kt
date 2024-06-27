package com.example.passwordmanagerassignment.ui.views
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//import com.example.passwordmanagerassignment.ui.views.biometric.BiometricPromptManager
//
//@Composable
//fun AuthenticationScreen(
//    navController: NavHostController, promptManager: BiometricPromptManager
//) {
//    val biometricResult by promptManager.promptResults.collectAsState(initial = null)
//
//    LaunchedEffect(biometricResult) {
//        if (biometricResult is BiometricPromptManager.BiometricResult.AuthenticationSuccess) {
//            navController.navigate("home") {
//                popUpTo("auth") { inclusive = true }
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Button(onClick = {
//            promptManager.showBiometricPrompt(
//                title = "Authenticate", description = "Please authenticate to continue to the app."
//            )
//        }) {
//            Text(text = "Authenticate")
//        }
//        biometricResult?.let { result ->
//            when (result) {
//                is BiometricPromptManager.BiometricResult.AuthenticationError -> Text(text = result.error)
//                BiometricPromptManager.BiometricResult.AuthenticationFailed -> Text(text = "Authentication Failed")
//                BiometricPromptManager.BiometricResult.AuthenticationNotSet -> Text(text = "Authentication Not Set")
//                BiometricPromptManager.BiometricResult.FeatureUnavailable -> Text(text = "Feature Unavailable")
//                BiometricPromptManager.BiometricResult.HardwareUnavailable -> Text(text = "Hardware Unavailable")
//                else -> {}
//            }
//        }
//    }
//}
