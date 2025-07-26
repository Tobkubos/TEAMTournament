package com.tobiaszkubiak.teamtournament.ui.customElements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tobiaszkubiak.teamtournament.data.User

@Composable
fun PlayersList(
    members: List<User>,
    onPlayerClick: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        items(
            items = members,
            key = { user -> user.uid }
        ) { user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${user.firstName} ${user.lastName}")

                IconButton(onClick = { onPlayerClick(user.uid) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = "Przejdź do profilu gracza"
                    )
                }
            }
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayersListPreview() {
    val sampleMembers = listOf(
        User(uid = "a", firstName = "Jan", lastName = "Kowalski"),
        User(uid = "b", firstName = "Anna", lastName = "Nowak"),
        User(uid = "c", firstName = "Piotr", lastName = "Zieliński")
    )
    PlayersList(
        members = sampleMembers,
        onPlayerClick = { userId ->
            println("Kliknięto gracza o ID: $userId")
        }
    )
}