package com.tobiaszkubiak.teamtournament.data

data class GroupInvite(
    val groupId: String = "",
    val groupName: String = "",
    val inviterId: String = "",
    val inviterName: String = "",
    val timestamp: Long = System.currentTimeMillis()
)