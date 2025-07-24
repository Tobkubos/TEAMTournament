package com.tobiaszkubiak.teamtournament.data

import com.google.firebase.firestore.DocumentId

data class User(
    val uid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    val isMaster: Boolean = false
)
