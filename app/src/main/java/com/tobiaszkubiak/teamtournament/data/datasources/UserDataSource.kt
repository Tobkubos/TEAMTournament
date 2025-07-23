package com.tobiaszkubiak.teamtournament.data.datasources

import com.tobiaszkubiak.teamtournament.data.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserDataSource {
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    suspend fun registerAndSaveUser(
        firstName: String,
        lastName: String,
        phone: String,
        email: String,
        password: String
    ) {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val firebaseUser = authResult.user ?: throw Exception("Nie udało się utworzyć konta")

        val user = User(
            uid = firebaseUser.uid,
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            email = firebaseUser.email.toString()
        )

        firestore.collection("users").document(user.uid).set(user).await()
    }

    suspend fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun getUserData(): User? {
        val firebaseUser = auth.currentUser ?: return null
        val documentSnapshot = firestore.collection("users").document(firebaseUser.uid).get().await()
        return documentSnapshot.toObject(User::class.java)
    }

    fun logout() {
        auth.signOut()
    }
}