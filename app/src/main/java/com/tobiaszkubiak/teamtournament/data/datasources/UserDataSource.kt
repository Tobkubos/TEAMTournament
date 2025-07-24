package com.tobiaszkubiak.teamtournament.data.datasources

import com.tobiaszkubiak.teamtournament.data.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tobiaszkubiak.teamtournament.data.UserProfile
import com.tobiaszkubiak.teamtournament.data.UserStatistics
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class UserDataSource {
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    suspend fun registerAndSaveUser(
        firstName: String, lastName: String, phone: String, email: String, password: String
    ) {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val firebaseUser = authResult.user ?: throw Exception("Nie udało się utworzyć konta")

        val newUser = User(
            uid = firebaseUser.uid,
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            email = firebaseUser.email?: ""
        )
        val newUserStats = UserStatistics(
            uid = firebaseUser.uid
        )
        val batch = firestore.batch()
        val userDocRef = firestore.collection("users").document(firebaseUser.uid)
        batch.set(userDocRef, newUser)

        val statsDocRef = firestore.collection("user_statistics").document(firebaseUser.uid)
        batch.set(statsDocRef, newUserStats)

        batch.commit().await()
    }

    suspend fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    /*
    suspend fun getUserData(): UserProfile? {
        //val firebaseUser = auth.currentUser ?: return null
        //val documentSnapshot = firestore.collection("users").document(firebaseUser.uid).get().await()
        //return documentSnapshot.toObject(User::class.java)
        return try {
            val firebaseUser = auth.currentUser ?: return null
            coroutineScope {
                val userDeferred = async { firestore.collection("users").document(firebaseUser.uid).get().await() }
                val statsDeferred = async { firestore.collection("user_statistics").document(firebaseUser.uid).get().await() }

                val userDoc = userDeferred.await()
                val statsDoc = statsDeferred.await()

                val user = userDoc.toObject(User::class.java)
                val stats = statsDoc.toObject(UserStatistics::class.java)

                if (user != null && stats != null) {
                    UserProfile(user, stats)
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            null
        }
    }
    */
    suspend fun getUser(uid: String): User? {
        return firestore.collection("users").document(uid).get().await()
            .toObject(User::class.java)
    }

    suspend fun getUserStats(uid: String): UserStatistics? {
        return firestore.collection("user_statistics").document(uid).get().await()
            .toObject(UserStatistics::class.java)
    }

    fun logout() {
        auth.signOut()
    }
}