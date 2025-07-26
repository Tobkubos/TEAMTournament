package com.tobiaszkubiak.teamtournament.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tobiaszkubiak.teamtournament.data.Group
import com.tobiaszkubiak.teamtournament.data.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(private val groupRepository: GroupRepository) : ViewModel() {

    private val _groupCreationState = MutableStateFlow<GroupState>(GroupState.Idle)
    val groupCreationState: StateFlow<GroupState> = _groupCreationState

    private val _groupDeleteState = MutableStateFlow<GroupState>(GroupState.Idle)
    val groupDeleteState: StateFlow<GroupState> = _groupDeleteState

    private val _groupEditDataUpdateState = MutableStateFlow<GroupState>(GroupState.Idle)
    val groupEditDataUpdateState: StateFlow<GroupState> = _groupEditDataUpdateState

    fun createAndSaveGroup(
        groupName: String, groupDescription: String, groupCity: String
    ) {
        viewModelScope.launch {
            _groupCreationState.value = GroupState.Loading
            try {
                groupRepository.createAndSaveGroup(groupName, groupDescription, groupCity)
                _groupCreationState.value = GroupState.Success
            } catch (e: Exception) {
                _groupCreationState.value = GroupState.Error(e.message ?: "Nieznany błąd")
            }
        }
    }
    fun deleteGroup(
        groupId: String
    ) {
        viewModelScope.launch {
            _groupDeleteState.value = GroupState.Loading
            try {
                groupRepository.deleteGroup(groupId)
                _groupDeleteState.value = GroupState.Success
            } catch (e: Exception) {
                _groupDeleteState.value = GroupState.Error(e.message ?: "Nieznany błąd")
            }
        }
    }

    fun editGroupData(
        groupId: String,newName: String, newDescription: String, newCity: String, newIsOpenStatus: Boolean
    ) {
        viewModelScope.launch {
            _groupDeleteState.value = GroupState.Loading
            try {
                groupRepository.updateGroupData(groupId, newName, newDescription, newCity, newIsOpenStatus)
                _groupEditDataUpdateState.value = GroupState.Success
            } catch (e: Exception) {
                _groupEditDataUpdateState.value = GroupState.Error(e.message ?: "Nieznany błąd")
            }
        }
    }
}


sealed class GroupState {
    object Idle : GroupState()
    object Loading : GroupState()
    object Success : GroupState()
    data class Error(val message: String) : GroupState()
}