package com.example.teamtournament.ui.customElements

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


@Composable
fun PanelIcon(navButtonName: String,
              navButtonIcon: ImageVector,
              onClick: () -> Unit = {}
) {
    val buttonSize = 80.dp
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.size(buttonSize),
        contentPadding = PaddingValues(0.dp)
        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = navButtonIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
            )
            Text(
                text = navButtonName,
                textAlign = TextAlign.Center,
                fontSize = 9.sp
            )
        }
    }
}


@Preview
@Composable
fun BottomNavigation() {
    var selectedScreen by remember { mutableStateOf("Profil")}
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        //DODAĆ NAWIGACJĘ! navController.navigate("profile_screen_route")
        PanelIcon("Profil", Icons.Default.AccountCircle, onClick = {selectedScreen = "Profil"})
        PanelIcon("Turnieje", Icons.Default.Star, onClick = {selectedScreen = "Turnieje"})
        PanelIcon("Ustaiwenia", Icons.Default.Settings, onClick = {selectedScreen = "Ustawienia"})
        PanelIcon("Informacje", Icons.Default.Info, onClick = {selectedScreen = "Informacje"})
    }
}

@Preview
@Composable
fun PanelIconPreview() {
    PanelIcon("CUSTOM TEXT", Icons.Default.Home)
}