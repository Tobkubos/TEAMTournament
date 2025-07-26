package com.tobiaszkubiak.teamtournament.ui.theme.panels

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tobiaszkubiak.teamtournament.data.Group
import com.tobiaszkubiak.teamtournament.data.User
import com.tobiaszkubiak.teamtournament.ui.customElements.GroupHeader
import com.tobiaszkubiak.teamtournament.ui.customElements.PlaceholderText
import com.tobiaszkubiak.teamtournament.ui.customElements.PlayersList

@Composable
fun GroupScreen(
    group: Group,
    members: List<User>
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Zawodnicy", "Drabinka", "Mecze")

    Column(modifier = Modifier.fillMaxSize()) {
        GroupHeader(group = group)

        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        // TODO: zmiana zakładki będzie zmieniać zawartość
                        selectedTabIndex = index
                    },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> PlayersList(members = members, onPlayerClick = {}) //TODO: KLIKNIĘCIE W GRACZA POKAZUJE JEGO PROFIL
            1 -> PlaceholderText(name = "Drabinka")
            2 -> PlaceholderText(name = "Mecze")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupScreenPreview() {
    val sampleGroup = Group(
        id = "1",
        name = "LIGA TEAM",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        members = listOf("a", "b", "c")
    )
    val sampleMembers = listOf(
        User(uid = "a", firstName = "Jan", lastName = "Urban"),
        User(uid = "b", firstName = "Geralt", lastName = "Rivia"),
        User(uid = "c", firstName = "Piotr", lastName = "Zieliński")
    )

    GroupScreen(group = sampleGroup, members = sampleMembers)
}