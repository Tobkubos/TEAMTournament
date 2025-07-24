package com.tobiaszkubiak.teamtournament.data.repository

import com.tobiaszkubiak.teamtournament.data.GroupInvite
import com.tobiaszkubiak.teamtournament.data.datasources.GroupInviteDataSource

class GroupInviteRepository(private val dataSource: GroupInviteDataSource) {

    suspend fun sendInvite(inviteeId: String, groupId: String){
        dataSource.sendInvite(inviteeId, groupId)
    }

    suspend fun acceptInvite(invite: GroupInvite){
        dataSource.acceptInvite(invite)
    }

    suspend fun declineInvite(invite: GroupInvite){
        dataSource.declineInvite(invite)
    }
}