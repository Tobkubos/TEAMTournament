package com.tobiaszkubiak.teamtournament

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.tobiaszkubiak.teamtournament.ui.theme.panels.LoginScreen
import com.tobiaszkubiak.teamtournament.ui.theme.panels.ProfileScreen
import com.tobiaszkubiak.teamtournament.ui.theme.panels.RegisterScreen
import com.tobiaszkubiak.teamtournament.ui.theme.panels.TournamentsScreen
import com.tobiaszkubiak.teamtournament.ui.theme.panels.SettingsScreen
import com.tobiaszkubiak.teamtournament.ui.theme.panels.InformationScreen
import com.tobiaszkubiak.teamtournament.ui.theme.TEAMTournamentTheme


import com.tobiaszkubiak.teamtournament.ui.controllers.Screen
import com.tobiaszkubiak.teamtournament.ui.customElements.BottomNavigation
import com.tobiaszkubiak.teamtournament.ui.theme.panels.GroupCreationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TEAMTournamentTheme {
               MainApp()
            }
        }
    }
}

@Composable
fun MainApp(){
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == Screen.Tournaments.route ||
                currentRoute == Screen.Profile.route ||
                currentRoute == Screen.Settings.route ||
                currentRoute == Screen.Information.route)
            {
                BottomNavigation(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Tournaments.route) {
                TournamentsScreen(
                    onNavigateToGroupCreation = { navController.navigate(Screen.GroupCreationForm.route) }
                )
            }
            composable(route = Screen.GroupCreationForm.route) {
                GroupCreationScreen(
                    onGroupCreated = { navController.popBackStack() },
                    onGroupCreationCancel = {navController.popBackStack()}
                )
            }
            composable(route = Screen.Profile.route) {
                ProfileScreen()
            }
            composable(route = Screen.Settings.route) {
                SettingsScreen()
            }
            composable(route = Screen.Information.route) {
                InformationScreen()
            }
            composable(route = Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = { navController.navigate(Screen.Tournaments.route)},
                    onNavigateToRegister = { navController.navigate(Screen.Register.route)}
                )
            }
            composable(route = Screen.Register.route) {
                RegisterScreen(
                    onRegisterSuccess = { navController.navigate(Screen.Login.route)
                })
            }
        }
    }
}