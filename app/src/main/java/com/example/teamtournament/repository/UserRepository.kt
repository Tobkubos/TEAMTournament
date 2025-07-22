package com.example.teamtournament.repository

import com.example.teamtournament.data.User
import com.example.teamtournament.datasources.UserDataSource

class UserRepository(private val dataSource: UserDataSource) {
    suspend fun registerAndSaveUser(
        firstName: String,
        lastName: String,
        phone: String,
        email: String,
        password: String
    ) {
        dataSource.registerAndSaveUser(firstName, lastName, phone, email, password)
    }
    suspend fun loginUser(email: String, password: String) {
        dataSource.loginUser(email, password)
    }

    suspend fun getUserData(): User? {
        return dataSource.getUserData()
    }

    fun logout() {
        dataSource.logout()
    }
}