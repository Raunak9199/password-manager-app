package com.example.passwordmanagerassignment


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.passwordmanagerassignment.data.PasswordEntry
import com.example.passwordmanagerassignment.ui.theme.PasswordManagerAssignmentTheme
import com.example.passwordmanagerassignment.ui.views.AddPasswordScreen
import com.example.passwordmanagerassignment.ui.views.HomeScreen
import com.example.passwordmanagerassignment.ui.views.biometric.BiometricPromptManager
import com.example.passwordmanagerassignment.utils.EncryptionUtil
import com.example.passwordmanagerassignment.viewmodel.PasswordViewModel
import com.ramcosta.composedestinations.utils.composable
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import com.example.passwordmanagerassignment.viewmodel.PasswordSharedViewModel

class MainActivity : AppCompatActivity() {

    private val promptManager by lazy {
        BiometricPromptManager(this)
    }
    private val passwordSharedViewModel by viewModels<PasswordSharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        showAppContent()

    }
    private fun showAppContent(){
        setContent {
            PasswordManagerAssignmentTheme {

                val biometricResult by promptManager.promptResults.collectAsState(
                    initial = null
                )
                val enrollLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult(),
                    onResult = {
                        println("Activity result: $it")
                    }
                )

                LaunchedEffect(biometricResult) {
                    if(biometricResult is BiometricPromptManager.BiometricResult.AuthenticationNotSet){
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                            val enrollIntent  = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                                    )
                            }

                            enrollLauncher.launch(enrollIntent)
                        }
                    }
                }

                val navController = rememberNavController()
                val passwordViewModel: PasswordViewModel = viewModel()

                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                   if (biometricResult != BiometricPromptManager.BiometricResult.AuthenticationSuccess)  Button(onClick = {
                        promptManager.showBiometricPrompt(
                            title = "Authenticate",
                            description = "Please authenticate to continue to the app."
                        )
                    }) {
                        Text(text = "Authenticate")
                    }
                    biometricResult?.let { result ->
                        when (result) {
                            is BiometricPromptManager.BiometricResult.AuthenticationError ->
                                Text(text = result.error)
                            BiometricPromptManager.BiometricResult.AuthenticationFailed ->
                                Text(text = "Authentication Failed")
                            BiometricPromptManager.BiometricResult.AuthenticationNotSet ->
                                Text(text = "Authentication Not Set")
                            BiometricPromptManager.BiometricResult.AuthenticationSuccess ->
                                AppNavHost(navController = navController, passwordViewModel = passwordViewModel)
                            BiometricPromptManager.BiometricResult.FeatureUnavailable ->
                                Text(text = "Feature Unavailable")
                            BiometricPromptManager.BiometricResult.HardwareUnavailable ->
                                Text(text = "Hardware Unavailable")
                        }
                    }
//                    AppNavHost(navController = navController, passwordViewModel = passwordViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, passwordViewModel: PasswordViewModel) {
    val passwordSharedViewModel: PasswordSharedViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                passwordViewModel = passwordViewModel,
                onAddPassword = {
                    passwordSharedViewModel.clearPasswordEntry()
                    navController.navigate("addPassword") },
                onEditPassword = { passwordEntry ->
                  navController.currentBackStackEntry?.arguments?.putParcelable("passwordEntry", passwordEntry)
                    Log.d("MainActivity", "AppNavHost: $passwordEntry")
                    passwordSharedViewModel.setPasswordEntry(passwordEntry)
                    navController.navigate("addPassword"){
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable("addPassword") {navBackStackEntry->
//            val passwordEntry = navBackStackEntry.arguments?.getParcelable<PasswordEntry>("passwordEntry")
            val passwordEntry = passwordSharedViewModel.passwordEntry.value
            AddPasswordScreen(
                passwordViewModel = passwordViewModel,
                onAddPassword = { password ->
                    val encryptedPassword = EncryptionUtil.encrypt(password.encryptedPassword)
                    val encryptedEntry = password.copy(encryptedPassword = encryptedPassword)
                    if (passwordEntry == null) {
                        passwordViewModel.insertPassword(encryptedEntry)
                    } else {
                        passwordViewModel.updatePassword(encryptedEntry)
                    }
                    navController.popBackStack()
                },
                onDeletePassword = { passwordEntr ->
                    passwordViewModel.deletePassword(passwordEntr)
                    navController.popBackStack()
                },
                passwordEntry = passwordEntry,
                onDismiss = { navController.popBackStack() }
            )
        }
    }
}