package com.example.bookcraftapplication.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bookcraftapplication.SignUp
import com.example.bookcraftapplication.navigateSingleTopTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment  = Alignment.End
        ) {
            Text("Login Screen", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.size(20.dp))

            // Use state to store the entered email and password
            val (email, setEmail) = remember { mutableStateOf("") }
            val (password, setPassword) = remember { mutableStateOf("") }

            TextField(
                value = email,
                onValueChange = { setEmail(it) },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                isError = !isValidEmail(email)
            )
            Spacer(modifier = Modifier.size(6.dp))
            TextField(
                value = password,
                onValueChange = { setPassword(it) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = !isValidPassword(password)
            )
            Spacer(modifier = Modifier.size(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    onClick = {
                        // Validate email and password before login logic
                        if (isValidEmail(email) && isValidPassword(password)) {


                        }
                    },
                    modifier = Modifier
                        .size(width = 100.dp, height = 40.dp)
                        .fillMaxWidth()
                ) {
                    Text("Login")
                }

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.CenterEnd) {

                Button(
                    onClick = {
                        navController.navigateSingleTopTo(SignUp.route);
                    },
                    modifier = Modifier
                ) {
                    Text("Sign up")
                }
            }
        }
    }
}

// Function to validate the email format using a simple regex
private fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return email.matches(emailRegex.toRegex())
}

// Function to validate the password format using a simple regex
private fun isValidPassword(password: String): Boolean {
    val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
    return password.matches(passwordRegex.toRegex())
}
