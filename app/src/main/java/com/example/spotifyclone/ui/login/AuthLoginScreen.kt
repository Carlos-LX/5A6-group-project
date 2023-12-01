package com.example.kotlinwithcompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotifyclone.auth.AuthViewModel
import com.example.spotifyclone.auth.AuthViewModelFactory
import com.example.spotifyclone.auth.ResultAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.launch

@Composable
fun AuthLoginScreen(authViewModel: AuthViewModel =
                                viewModel(factory= AuthViewModelFactory())
    ) {
    val userState = authViewModel.currentUser().collectAsState()

    val signUpResult by authViewModel.signUpResult.collectAsState(null)
    val signInResult by authViewModel.signInResult.collectAsState(null)
    val signOutResult by authViewModel.signOutResult.collectAsState(null)
    val deleteAccountResult by authViewModel.deleteAccountResult.collectAsState(null)

    val snackbarHostState = remember { SnackbarHostState() } // Material 3 approach

    // Show a Snackbar when sign-in is successful
    LaunchedEffect(signUpResult) {
        signUpResult?.let {
            if (it is ResultAuth.Success) {
                snackbarHostState.showSnackbar("Sign-up Successful")
            } else {
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

    Column {
        // Snackbar to display messages
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(16.dp)
        )
        if (userState.value == null) {
            Text("Not logged in")
            Button(onClick = {
                authViewModel.signUp("myname2@name.com", "Abcd1234!")
            }) {
                Text("Sign up via email")
            }
            Button(onClick = {
                authViewModel.signIn("myname2@name.com", "Abcd1234!")
            }) {
                Text("Sign in via email")
            }
        } else {
            if (userState.value==null)
                Text("Please sign in")
            else
                Text("Welcome ${userState.value!!.email}")
            Button(onClick = {
                authViewModel.signOut()
            }) {
                Text("Sign out")
            }
            Button(onClick = {
                authViewModel.delete()
            }) {
                Text("Delete account")
            }
        }
    }
}