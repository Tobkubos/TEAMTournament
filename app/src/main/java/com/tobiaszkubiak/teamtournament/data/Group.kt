package com.tobiaszkubiak.teamtournament.data

import com.google.firebase.firestore.DocumentId

data class Group (
    @DocumentId
    val id: String = "",
    val ownerId: String = "",
    val members: List<String> = emptyList(),
    val name: String = "",
    val description: String = "",
    val city: String = "",
    val isOpenForNewMembers: Boolean = false,
)
