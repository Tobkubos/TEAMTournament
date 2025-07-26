package com.tobiaszkubiak.teamtournament.ui.theme.panels

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tobiaszkubiak.teamtournament.data.Group
import com.tobiaszkubiak.teamtournament.data.viewmodels.AuthViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.tobiaszkubiak.teamtournament.ui.customElements.GroupListItem
import com.tobiaszkubiak.teamtournament.ui.customElements.ProfileInfoRow

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val userProfile by viewModel.currentUserProfile.collectAsState()
    val userGroups by viewModel.userGroups.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadCurrentUserProfile()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (userProfile != null) {
            Text("Profil Użytkownika", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            ProfileInfoRow("Imię:", userProfile!!.user.firstName)
            ProfileInfoRow("Nazwisko:", userProfile!!.user.lastName)
            ProfileInfoRow("Telefon:", userProfile!!.user.phone)


            Spacer(modifier = Modifier.height(32.dp))
            Text("Moje Grupy", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            if (userGroups.isEmpty()) {
                Text("Nie należysz do żadnej grupy.")
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(userGroups) { group ->
                        GroupListItem(group = group, onGroupClick = {})
                        Divider()
                    }
                }
            }

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



