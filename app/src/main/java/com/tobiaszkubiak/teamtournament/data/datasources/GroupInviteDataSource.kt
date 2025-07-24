package com.tobiaszkubiak.teamtournament.data.datasources
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.tobiaszkubiak.teamtournament.data.Group
import com.tobiaszkubiak.teamtournament.data.GroupInvite
import com.tobiaszkubiak.teamtournament.data.User
import kotlinx.coroutines.tasks.await

class GroupInviteDataSource {
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    suspend fun sendInvite(inviteeId: String, groupId: String){
        val inviter = auth.currentUser ?: throw IllegalStateException("Tylko zalogowany użytkownik może wysyłać zaproszenia")

        val inviterDoc = firestore.collection("users").document(inviter.uid).get().await()
        val inviterUser = inviterDoc.toObject(User::class.java) ?: throw IllegalStateException("Nie znaleziono danych zapraszającego")

        val groupDoc = firestore.collection("groups").document(groupId).get().await()
        val group = groupDoc.toObject(Group::class.java) ?: throw IllegalStateException("Grupa nie istnieje")

        val newInvite = GroupInvite(
            groupId = groupId,
            groupName = group.name,
            inviterId = inviter.uid,
            inviterName = "${inviterUser.firstName} ${inviterUser.lastName}".trim()
        )

        firestore.collection("users").document(inviteeId)
            .collection("invites").document(groupId)
            .set(newInvite)
            .await()
    }

    suspend fun getPendingInvites(): List<GroupInvite> {
        val currentUser = auth.currentUser ?: return emptyList()

        val invitesSnapshot = firestore.collection("users").document(currentUser.uid)
            .collection("invites")
            .orderBy("timestamp")
            .get()
            .await()
        return invitesSnapshot.toObjects(GroupInvite::class.java)
    }

    suspend fun acceptInvite(invite: GroupInvite) {
        val currentUser = auth.currentUser ?: throw IllegalStateException("Użytkownik musi być zalogowany")
        val batch = firestore.batch()

        val groupRef = firestore.collection("groups").document(invite.groupId)
        batch.update(groupRef, "members", FieldValue.arrayUnion(currentUser.uid))

        val inviteRef = firestore.collection("users").document(currentUser.uid)
            .collection("invites").document(invite.groupId)
        batch.delete(inviteRef)

        batch.commit().await()
    }

    suspend fun declineInvite(invite: GroupInvite) {
        val currentUser = auth.currentUser ?: throw IllegalStateException("Użytkownik musi być zalogowany")

        firestore.collection("users").document(currentUser.uid)
            .collection("invites").document(invite.groupId)
            .delete()
            .await()
    }
}