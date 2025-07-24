package com.tobiaszkubiak.teamtournament.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tobiaszkubiak.teamtournament.data.User
import com.tobiaszkubiak.teamtournament.data.UserProfile
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

    suspend fun getUserProfile(): UserProfile? {
        val firebaseUser = Firebase.auth.currentUser ?: return null
        val uid = firebaseUser.uid

        val user = dataSource.getUser(uid)
        val stats = dataSource.getUserStats(uid)

        return if (user != null && stats != null) {
            UserProfile(user, stats)
        } else {
            null
        }
    }

    fun logout() {
        dataSource.logout()
    }
}