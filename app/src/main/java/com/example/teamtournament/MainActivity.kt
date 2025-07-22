package com.example.teamtournament

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.teamtournament.ui.theme.panels.LoginScreen
import com.example.teamtournament.ui.theme.panels.ProfileScreen
import com.example.teamtournament.ui.theme.panels.RegisterScreen
import com.example.teamtournament.ui.theme.TEAMTournamentTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TEAMTournamentTheme {
                var currentScreen by remember {
                    val initialScreen = if (Firebase.auth.currentUser != null) "profile" else "login"
                    mutableStateOf(initialScreen)
                }

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    when (currentScreen) {
                        "login" -> LoginScreen(
                            onLoginSuccess = { currentScreen = "profile" },
                            onNavigateToRegister = { currentScreen = "register" }
                        )
                        "register" -> RegisterScreen(
                            onRegisterSuccess = { currentScreen = "login" }
                        )
                        "profile" -> ProfileScreen(
                            onLogout = { currentScreen = "login" }
                        )
                    }
                }
            }
        }
    }
}