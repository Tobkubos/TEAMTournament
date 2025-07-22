package com.example.teamtournament.data

import com.google.firebase.firestore.DocumentId

data class UserStatistics (
    @DocumentId
    val uid: String = "",
    val memberOfGroupIds: List<String> = emptyList(),
    val matchIds: List<String> = emptyList(),
    val overallWins: Int = 0,
    val overallLoses: Int = 0
)