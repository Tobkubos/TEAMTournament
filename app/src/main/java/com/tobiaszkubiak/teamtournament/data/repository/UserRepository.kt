package com.tobiaszkubiak.teamtournament.data.repository

import com.tobiaszkubiak.teamtournament.data.User
import com.tobiaszkubiak.teamtournament.data.datasources.UserDataSource

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