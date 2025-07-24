package com.tobiaszkubiak.teamtournament.data.datasources

import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tobiaszkubiak.teamtournament.data.Group
import com.tobiaszkubiak.teamtournament.data.User
import kotlinx.coroutines.tasks.await

class GroupDataSource {
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    suspend fun createAndSaveGroup(
        groupName: String, groupDescription: String, groupCity: String
    ) {
        val currentUser = auth.currentUser ?: throw IllegalStateException("Użytkownik musi być zalogowany")

        val newGroup = Group(
            ownerId = currentUser.uid,
            members = listOf(currentUser.uid),
            name = groupName,
            description = groupDescription,
            city = groupCity,
        )

        val groupDocument = firestore.collection("groups").add(newGroup).await()
        val newGroupId = groupDocument.id

        val userDataRef = firestore.collection("user_data").document(currentUser.uid)
        userDataRef.update("memberOfGroupIds", FieldValue.arrayUnion(newGroupId)).await()
    }

    suspend fun deleteGroup(groupId: String) {
        val currentUser = auth.currentUser ?: throw IllegalStateException("Użytkownik musi być zalogowany")

        val groupRef = firestore.collection("groups").document(groupId)
        val groupDoc = groupRef.get().await()
        val group = groupDoc.toObject(Group::class.java)
            ?: throw IllegalStateException("Grupa nie istnieje")

        if (currentUser.uid != group.ownerId) {
            throw SecurityException("Tylko właściciel może usunąć grupę")
        }

        val batch = firestore.batch()
        batch.delete(groupRef)

        for (memberId in group.members) {
            val userDataRef = firestore.collection("user_data").document(memberId)
            // FieldValue.arrayRemove - safe delete
            batch.update(userDataRef, "memberOfGroupIds", FieldValue.arrayRemove(groupId))
        }
        batch.commit().await()
    }

    suspend fun editGroupData(groupId: String,newName: String, newDescription: String, newCity: String, newIsOpenStatus: Boolean){
        val currentUser = auth.currentUser ?: throw IllegalStateException("Użytkownik musi być zalogowany")

        val groupRef = firestore.collection("groups").document(groupId)
        val groupDoc = groupRef.get().await()

        val ownerId = groupDoc.getString("ownerId") ?: throw IllegalStateException("Nie znaleziono właściciela grupy")

        if (currentUser.uid != ownerId) {
            throw SecurityException("Tylko właściciel może edytować dane grupy")
        }

        val updates = mapOf(
            "name" to newName,
            "description" to newDescription,
            "city" to newCity,
            "isOpenForNewMembers" to newIsOpenStatus
        )

        groupRef.update(updates).await()
    }
}