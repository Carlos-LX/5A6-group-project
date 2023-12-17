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
import com.example.bookcraftapplication.Login
import com.example.bookcraftapplication.navigateSingleTopTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Sign-Up Screen", style = MaterialTheme.typography.headlineSmall)

            // Use state to store the entered username, email, password, and confirmPassword
            val (username, setUsername) = remember { mutableStateOf("") }
            val (email, setEmail) = remember { mutableStateOf("") }
            val (password, setPassword) = remember { mutableStateOf("") }
            val (confirmPassword, setConfirmPassword) = remember { mutableStateOf("") }
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = username,
                onValueChange = { setUsername(it) },
                label = { Text("Username") }
            )
            Spacer(modifier = Modifier.size(6.dp))
            TextField(
                value = email,
                onValueChange = { setEmail(it) },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                isError = !isValidEmail(email),

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
            TextField(
                value = confirmPassword,
                onValueChange = { setConfirmPassword(it) },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = !isValidPassword(confirmPassword) || password != confirmPassword
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Button(
                    onClick = {
                        // Validate email and password before sign-up logic
                        if (isValidEmail(email) && isValidPassword(password) && password == confirmPassword) {
                            // Handle sign-up logic here using the entered values
                            // Call the sign-up function or navigate to the sign-up logic passing the user credentials
                        }
                    },
                    modifier = Modifier
                        .size(width = 100.dp, height = 40.dp)
                        .fillMaxWidth() // Ensure the Button takes the full width inside the Box
                ) {
                    Text("Sign Up")
                }
            }



            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Button(
                    onClick = {
                        navController.navigateSingleTopTo(Login.route);
                    },
                    modifier = Modifier
                ) {
                    Text("Log in")
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
