package com.example.teamtournament.data

import com.google.firebase.firestore.DocumentId

data class MatchDetails(
    val match: Match,
    val homeUser: User,
    val guestUser: User
)

data class Match (
    @DocumentId
    val id: String = "",
    val groupId: String = "",
    val uidHome: String = "",
    val uidGuest: String = "",
    val uidWinner: String = "",
    val sets: List<Set> = emptyList(),
    val tieBreak: TieBreak? = null
)

data class Set(
    val uidHomeGems: Int = 0,
    val uidGuestGems: Int = 0,
)

data class TieBreak(
    val uidHomePoints: Int = 0,
    val uidGuestPoints: Int = 0,
)