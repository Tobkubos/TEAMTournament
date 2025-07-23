package com.tobiaszkubiak.teamtournament.ui.customElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tobiaszkubiak.teamtournament.ui.controllers.Screen

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


@Composable
fun BottomNavigation(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        PanelIcon("Profil", Icons.Default.AccountCircle, onClick = {
            if (Firebase.auth.currentUser != null) {
            navController.navigate(Screen.Profile.route)
            }
            else {
                navController.navigate(Screen.Login.route)
            }
        })
        PanelIcon("Turnieje", Icons.Default.Star, onClick = { navController.navigate(Screen.Tournaments.route)})
        PanelIcon("Ustaiwenia", Icons.Default.Settings, onClick = {
            if (Firebase.auth.currentUser != null) {
                navController.navigate(Screen.Settings.route)
            }
            else {
                navController.navigate(Screen.Login.route)
            }
        })
        PanelIcon("Informacje", Icons.Default.Info, onClick = {
            navController.navigate(Screen.Information.route)})
    }
}

@Preview
@Composable
fun PanelIconPreview() {
    PanelIcon("CUSTOM TEXT", Icons.Default.Home)
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    val navController = rememberNavController()
    BottomNavigation(navController = navController)
}