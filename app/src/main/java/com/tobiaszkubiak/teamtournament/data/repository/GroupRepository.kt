package com.tobiaszkubiak.teamtournament.data.repository

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
}