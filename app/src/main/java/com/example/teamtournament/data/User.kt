package com.example.teamtournament.data

data class UserProfile(
    val user: User,
    val stats: UserStatistics
)

data class User(
    val uid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    val isMaster: Boolean = false
)