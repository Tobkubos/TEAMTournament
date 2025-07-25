package com.tobiaszkubiak.teamtournament.data.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tobiaszkubiak.teamtournament.data.Group
import com.tobiaszkubiak.teamtournament.data.datasources.GroupDataSource

class GroupRepository(private val dataSource: GroupDataSource) {

    suspend fun createAndSaveGroup(groupName: String, groupDescription: String, groupCity: String) {
        dataSource.createAndSaveGroup(groupName, groupDescription, groupCity)
    }

    suspend fun deleteGroup(groupId: String) {
        dataSource.deleteGroup(groupId)
    }

    suspend fun updateGroupData(groupId: String, newName: String, newDescription: String, newCity: String, newIsOpenStatus: Boolean
    ) {
        dataSource.editGroupData(groupId, newName, newDescription, newCity, newIsOpenStatus)
    }

    suspend fun getGroupsByIds(groupIds: List<String>): List<Group>{
        return dataSource.getGroupsByIds(groupIds)
    }
}