package com.tobiaszkubiak.teamtournament.ui.theme.panels

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tobiaszkubiak.teamtournament.data.datasources.GroupDataSource
import com.tobiaszkubiak.teamtournament.data.repository.GroupRepository
import com.tobiaszkubiak.teamtournament.data.viewmodels.GroupViewModel
import com.tobiaszkubiak.teamtournament.data.viewmodels.GroupViewModelFactory
import com.tobiaszkubiak.teamtournament.data.viewmodels.GroupState

@Composable
fun GroupCreationScreen(
    onGroupCreated: () -> Unit,
    onGroupCreationCancel: () -> Unit
){
    val groupDataSource = remember { GroupDataSource() }
    val groupRepository = remember { GroupRepository(groupDataSource) }
    val viewModelFactory = remember { GroupViewModelFactory(groupRepository) }
    val viewModel: GroupViewModel = viewModel(factory = viewModelFactory)
    // ---------------------------------------------------------------------------

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    val creationState by viewModel.groupCreationState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Utwórz nową grupę", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("description") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("city") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when (creationState) {
                is GroupState.Loading -> {
                    CircularProgressIndicator()
                }
                else -> {
                    Button(
                        onClick = {
                            if (name.isNotBlank() && description.isNotBlank() && city.isNotBlank()) {
                                viewModel.createAndSaveGroup(name, description, city)
                            } else {
                                Toast.makeText(context, "Wypełnij poprawnie wszystkie pola", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Stwórz grupę")
                    }
                }
            }
        }
    }

    LaunchedEffect(creationState) {
        if (creationState is GroupState.Success) {
            Toast.makeText(context, "Grupa utworzona!", Toast.LENGTH_SHORT).show()
            onGroupCreated()
        }
        if (creationState is GroupState.Error) {
            Toast.makeText(context, "Błąd: ${(creationState as GroupState.Error).message}", Toast.LENGTH_LONG).show()
        }
    }
}