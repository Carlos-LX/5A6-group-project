package com.example.bookcraftapplication.ui.login

// import com.example.bookcraftapplication.Informational
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookcraftapplication.Informational
import com.example.bookcraftapplication.LocalNavController
import com.example.bookcraftapplication.R
import com.example.bookcraftapplication.SignUp
import com.example.bookcraftapplication.auth.AuthViewModel
import com.example.bookcraftapplication.auth.AuthViewModelFactory
import com.example.bookcraftapplication.auth.ResultAuth
import com.example.bookcraftapplication.data.userEmail
import com.example.bookcraftapplication.navigateSingleTopTo
import com.google.firebase.firestore.FirebaseFirestore

/**
 * The screen that the user sees in order to login
 */
@Composable
 fun AuthLoginScreen(authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory()), db: FirebaseFirestore) {
     val navController = LocalNavController.current
     var isSubmitted = false
     val signInResult by authViewModel.signInResult.collectAsState(ResultAuth.Inactive)

     val snackbarHostState = remember { SnackbarHostState() } // Material 3 approach
     val (email, setEmail) = remember { mutableStateOf("") }
     val (password, setPassword) = remember { mutableStateOf("") }

     // Show a Snackbar when sign-in is successful
     LaunchedEffect(signInResult) {
         signInResult?.let {
             if (it is ResultAuth.Inactive) {
                 println("SignIn LaunchedEffect: Inactive")
                 return@LaunchedEffect
             }
             if (it is ResultAuth.InProgress) {
                 println("SignIn LaunchedEffect: InProgress")
                 snackbarHostState.showSnackbar("Sign-in In Progress")
                 return@LaunchedEffect
             }
             if (it is ResultAuth.Success && it.data) {
                 userEmail = email
                 snackbarHostState.showSnackbar("Sign-in Successful")
                 checkIfUserExistsInFirestore(email, db)
                 navController.navigateSingleTopTo(Informational.route)
             } else if (it is ResultAuth.Failure || it is ResultAuth.Success) {
                 snackbarHostState.showSnackbar("Sign-in Unsuccessful. Email or password is invalid.")
             }
         }
     }
    /**
     * Function that calls the sign in function when it is clicked. Essential because of the Keyboard Options in the text field
     */
    fun signInButtonClick() {
        if (isValidEmail(email) && isValidPassword(password)) {
            // Valid email and password, proceed with sign-in
            authViewModel.signIn(email, password)
            isSubmitted = true
        } else {
            isSubmitted = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            horizontalAlignment = Alignment.End,
        ) {
            item {
                val focusManager = LocalFocusManager.current
                Text(
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                )
                Spacer(modifier = Modifier.size(20.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { setEmail(it) },
                    label = { Text("Email") },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next, // Set Next for the email field
                        ),
                    keyboardActions =
                        KeyboardActions(
                            onNext = {
                                // Request focus on the password field when Enter is pressed on the email field
                                focusManager.moveFocus(FocusDirection.Down)
                            },
                        ),
                    isError = !isValidEmail(email),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                )
                Spacer(modifier = Modifier.size(24.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { setPassword(it) },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = !isValidPassword(password),
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done, // Set Done for the password field
                        ),
                    keyboardActions =
                        KeyboardActions(
                            onDone = {
                                // Simulate a click on the sign-in button when Enter is pressed on the password field
                                signInButtonClick()
                            },
                        ),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                )
                Spacer(modifier = Modifier.size(40.dp))
                // Sign-in Button
                Button(
                    onClick = {
                        signInButtonClick()
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                ) {
                    Text("Sign In")
                }
                Spacer(modifier = Modifier.size(40.dp))
            }

            item {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Divider(
                        modifier =
                            Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                        thickness = 2.dp,
                    )
                    Text(
                        text = "OR",
                        fontWeight = FontWeight.Bold,
                    )
                    Divider(
                        modifier =
                            Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                        thickness = 2.dp,
                    )
                }
            }

             item {
                 Spacer(modifier = Modifier.size(40.dp))
                 // Sign-up Button
                 Button(onClick = {
                     // Navigate to the sign-up screen
                     navController.navigateSingleTopTo(SignUp.route)
                 }, modifier = Modifier
                     .fillMaxWidth()) {
                     Text("Sign Up")
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
 }

 private fun checkIfUserExistsInFirestore(email: String, db: FirebaseFirestore) {
     db.collection("user-profile")
         .whereEqualTo("Email", email)
         .get()
         .addOnSuccessListener { documents ->
             if (documents.isEmpty) {
                 createUserDocument(email, db)
             }
         }
         .addOnFailureListener { exception ->
             Log.e("AuthLoginScreen", "Error checking user existence: $exception")
         }
 }

 private fun createUserDocument(email: String, db: FirebaseFirestore) {
     val newUser = hashMapOf(
         "Email" to email,
         "Favorites" to emptyList<String>()
     )

     db.collection("user-profile")
         .add(newUser)
         .addOnSuccessListener { documentReference ->
             Log.d("AuthLoginScreen", "User document created with ID: ${documentReference.id}")
         }
         .addOnFailureListener { e ->
             Log.e("AuthLoginScreen", "Error creating user document: $e")
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
