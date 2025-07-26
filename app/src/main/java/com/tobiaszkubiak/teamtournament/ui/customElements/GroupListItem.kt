package com.tobiaszkubiak.teamtournament.ui.customElements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tobiaszkubiak.teamtournament.data.Group

@Composable
fun GroupListItem(
    group: Group,
    onGroupClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = group.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = group.city,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Członków: ${group.members.size}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = { onGroupClick(group.id) }) {
            Text("Zobacz")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupListItemWithButtonPreview() {
    GroupListItem(
        group = Group(id = "123", "Drużyna Pierścienia", members = listOf("a", "b", "c"), city = "Aisengard"),
        onGroupClick = { groupId ->
            println("Kliknięto grupę o ID: $groupId")
        }
    )
}