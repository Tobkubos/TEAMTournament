package com.tobiaszkubiak.teamtournament.ui.controllers

sealed class Screen(val route: String) {
    object Login : Screen("LoginScreen")
    object Register : Screen("RegisterScreen")
    object Profile : Screen("ProfileScreen")
    object Tournaments: Screen("TournamentsScreen")
    object Settings: Screen("SettingsScreen")
    object Information: Screen("InformationScreen")
    object GroupCreationForm: Screen("GroupCreationScreen")
}