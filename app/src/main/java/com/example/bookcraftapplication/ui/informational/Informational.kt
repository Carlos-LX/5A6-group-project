package com.example.bookcraftapplication.ui.informational

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.LibraryBooks
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookcraftapplication.LocalNavController
import com.example.bookcraftapplication.Login
import com.example.bookcraftapplication.auth.AuthViewModel
import com.example.bookcraftapplication.auth.AuthViewModelFactory
import com.example.bookcraftapplication.data.userEmail
import com.example.bookcraftapplication.navigateSingleTopTo

@Composable
fun Informational(authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = LocalNavController.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        verticalArrangement = Arrangement.spacedBy(70.dp)
    ) {
        item {
            InformationalItem(
                icon = Icons.Rounded.Favorite,
                text = "Navigate to Favorites to check out bookmarked books."
            )
        }
        item {
            InformationalItem(
                icon = Icons.Rounded.LibraryBooks,
                text = "Navigate to Library to find books or add your opinion on them."
            )
        }
        item {
            InformationalItem(
                icon = Icons.Rounded.Settings,
                text = "Navigate to Settings to change the theme."
            )
        }
        item {
            Button(onClick = {
                authViewModel.signOut()
                userEmail = ""
                navController.navigateSingleTopTo(Login.route)
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Sign Out")
            }
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(16.dp)
    ) { snackbarData ->
        Snackbar(
            modifier = Modifier.fillMaxWidth(),
            snackbarData = snackbarData
        )
    }
}

@Composable
fun InformationalItem(icon: ImageVector, text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(bottom = 8.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(text = text)
    }
}