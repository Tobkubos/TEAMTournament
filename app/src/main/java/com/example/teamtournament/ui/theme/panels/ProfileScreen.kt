package com.example.teamtournament.ui.theme.panels

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teamtournament.datasources.UserDataSource
import com.example.teamtournament.repository.UserRepository
import com.example.teamtournament.ui.theme.viewmodels.AuthViewModel
import com.example.teamtournament.ui.theme.viewmodels.AuthViewModelFactory

@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {
    val dataSource = remember { UserDataSource() }
    val userRepository = remember { UserRepository(dataSource) }
    val viewModelFactory = remember { AuthViewModelFactory(userRepository) }
    val viewModel: AuthViewModel = viewModel(factory = viewModelFactory)

    val user by viewModel.currentUser.collectAsState()

    LaunchedEffect(Unit) {
        if (user == null) {
            viewModel.loadCurrentUser()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (user != null) {
            Text("Profil Użytkownika", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            ProfileInfoRow("Imię:", user!!.firstName)
            ProfileInfoRow("Nazwisko:", user!!.lastName)
            ProfileInfoRow("Telefon:", user!!.phone)

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                viewModel.logout()
                onLogout()
            }) {
                Text("Wyloguj")
            }
        } else {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, modifier = Modifier.weight(1f), fontSize = 18.sp)
        Text(text = value, modifier = Modifier.weight(2f), fontSize = 18.sp)
    }
    Spacer(modifier = Modifier.height(8.dp))
}