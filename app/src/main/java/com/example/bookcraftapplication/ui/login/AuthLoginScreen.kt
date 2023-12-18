 package com.example.bookcraftapplication.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bookcraftapplication.R
import com.example.bookcraftapplication.SignUp
import com.example.bookcraftapplication.auth.AuthViewModel
import com.example.bookcraftapplication.auth.AuthViewModelFactory
import com.example.bookcraftapplication.auth.ResultAuth
import com.example.bookcraftapplication.navigateSingleTopTo

 @Composable
fun AuthLoginScreen(authViewModel: AuthViewModel = viewModel(factory= AuthViewModelFactory()), navController: NavHostController) {
    val userState = authViewModel.currentUser().collectAsState()

    val signUpResult by authViewModel.signUpResult.collectAsState(ResultAuth.Inactive)
    val signInResult by authViewModel.signInResult.collectAsState(null)
    val signOutResult by authViewModel.signOutResult.collectAsState(null)
    val deleteAccountResult by authViewModel.deleteAccountResult.collectAsState(null)

    val snackbarHostState = remember { SnackbarHostState() } // Material 3 approach

    // Show a Snackbar when sign-up is successful, etc.
    LaunchedEffect(signUpResult) {
        signUpResult?.let {
            if (it is ResultAuth.Inactive) {
                return@LaunchedEffect
            }
            if (it is ResultAuth.InProgress) {
                snackbarHostState.showSnackbar("Sign-up In Progress")
                return@LaunchedEffect
            }
            if (it is ResultAuth.Success && it.data) {
                snackbarHostState.showSnackbar("Sign-up Successful")
            } else if (it is ResultAuth.Failure || it is ResultAuth.Success) { // success(false) case
                snackbarHostState.showSnackbar("Sign-up Unsuccessful")
            }
        }
    }

    // Show a Snackbar when sign-in is successful
    LaunchedEffect(signInResult) {
        signInResult?.let {
            if (it is ResultAuth.Success && it.data) {
                snackbarHostState.showSnackbar("Sign-in Successful")
            } else {
                snackbarHostState.showSnackbar("Sign-in Unsuccessful")
            }
        }
    }

    // Show a Snackbar when sign-out is successful
    LaunchedEffect(signOutResult) {
        signOutResult?.let {
            if (it is ResultAuth.Success && it.data) {
                snackbarHostState.showSnackbar("Sign-out Successful")
            } else
                snackbarHostState.showSnackbar("Sign-out Unsuccessful")
        }
    }


    // Show a Snackbar when account deletion is successful
    LaunchedEffect(deleteAccountResult) {
        deleteAccountResult?.let {
            if (it is ResultAuth.Success && it.data) {
                snackbarHostState.showSnackbar("Account Deleted")
            } else {
                snackbarHostState.showSnackbar("Deletion failed")
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment  = Alignment.End
        ) {
            item {
                Text(
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
                Spacer(modifier = Modifier.size(20.dp))

                val (email, setEmail) = remember { mutableStateOf("") }
                val (password, setPassword) = remember { mutableStateOf("") }

                TextField(
                    value = email,
                    onValueChange = { setEmail(it) },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    isError = !isValidEmail(email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
                Spacer(modifier = Modifier.size(24.dp))
                TextField(
                    value = password,
                    onValueChange = { setPassword(it) },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = !isValidPassword(password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
                Spacer(modifier = Modifier.size(40.dp))
                Button(onClick = {  }, modifier = Modifier
                        .fillMaxWidth() // This makes the button fill the maximum width available
                        .padding(vertical = 16.dp)) {
                    Text("Sign In")
                }
                Spacer(modifier = Modifier.size(40.dp))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        color = Color.Gray,
                        thickness = 2.dp
                    )
                    Text(
                        text = "OR",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        color = Color.Gray,
                        thickness = 2.dp
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.size(40.dp))
                Button(onClick = {  }, modifier = Modifier
                    .fillMaxWidth()) {
                    Text("Sign Up")
                }
            }
        }
    }

//    Column {
//        // Snackbar to display messages
//        SnackbarHost(
//            hostState = snackbarHostState,
//            modifier = Modifier.padding(16.dp)
//        )
//        if (userState.value == null) {
//            Text("Not logged in")
//            Button(onClick = {
//                authViewModel.signUp("myname2@name.com", "Abcd1234!")
//            }) {
//                Text("Sign up via email")
//            }
//            Button(onClick = {
//                authViewModel.signIn("myname2@name.com", "Abcd1234!")
//            }) {
//                Text("Sign in via email")
//            }
//        } else {
//            if (userState.value==null)
//                Text("Please sign in")
//            else
//                Text("Welcome ${userState.value!!.email}")
//            Button(onClick = {
//                authViewModel.signOut()
//            }) {
//                Text("Sign out")
//            }
//            Button(onClick = {
//                authViewModel.delete()
//            }) {
//                Text("Delete account")
//            }
//        }
//    }
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
