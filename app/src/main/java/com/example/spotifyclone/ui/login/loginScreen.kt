package com.example.spotifyclone.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Login Screen", style = MaterialTheme.typography.headlineSmall)
            TextField(value = "", onValueChange = {}, label = { Text("Username") })
            TextField(value = "", onValueChange = {}, label = { Text("Password") })

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    onClick = { /* Handle login logic here */ },
                    modifier = Modifier
                        .size(width = 100.dp, height = 40.dp)
                        .fillMaxWidth() // Ensure the Button takes the full width inside the Box
                ) {
                    Text("Login")
                }
            }
        }
    }
}