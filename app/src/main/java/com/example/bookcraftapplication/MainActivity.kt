/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bookcraftapplication

// The vast majority of source code I got was from the kotlin bootcamp with some help using chat gpt.
// I mainly used chat gpt to aid with printing an actual bookItem when the add button is pressed, I
// overshot with the state and user input criteria and I struggled to figure it out on my own
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codelab.basics.R
import com.example.bookcraftapplication.ui.Details.Details
import com.example.bookcraftapplication.ui.theme.SpotifyCloneTheme
import com.example.bookcraftapplication.ui.books.BookCollection
import com.example.bookcraftapplication.ui.settings.Settings
import com.example.bookcraftapplication.ui.login.LoginScreen
import com.example.bookcraftapplication.ui.login.SignUpScreen
import com.example.bookcraftapplication.ui.library.Library
import com.example.bookcraft.data.focusedBook
import kotlinx.coroutines.*


enum class Theme {
    Light, Dark
}


private const val USER_PREFERENCES_NAME = "theme"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)
class MainActivity : ComponentActivity() {
    /* Always be able to access the module ("static") */
    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var preferences = PreferencesRepository(dataStore, this)
            var userPrefs by remember { mutableStateOf(UserPrefs(Theme.Light, 12.0f)) }






            // Collect the Flow and update userPrefs when it changes
            LaunchedEffect(preferences.userPreferencesFlow) {
                preferences.userPreferencesFlow.collect { newUserPrefs ->
                    userPrefs = newUserPrefs
                }
            }

                MyApp(
                    modifier = Modifier.fillMaxSize(),
                    selectedTheme = userPrefs.selectedTheme,
                    onThemeChange = { newTheme ->

                        CoroutineScope(Dispatchers.IO).launch {
                            preferences.setTheme(newTheme)
                        }
                    },
                    userfontSize = userPrefs.fontSize,
                    onFontChange = { newSize ->
                        CoroutineScope(Dispatchers.IO).launch {
                            preferences.setFontSize(newSize)
                        }
                    },
                )
            }
        }
    }



/**
 * The main composable function that defines the app's UI.
 * @param modifier Modifier for the Surface composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(modifier: Modifier = Modifier, selectedTheme: Theme, onThemeChange: (Theme) -> Unit, userfontSize: Float, onFontChange: (Float) -> Unit) {

    modifier
    SpotifyCloneTheme(currentTheme = selectedTheme) {
        // Initialize the navigation controller
        val navController = rememberNavController()
        var selectedOption = false;
        Scaffold(
            modifier = modifier,
            topBar = { BookTopAppBar() },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier,

                    ) {
                    Row {
                        // Existing code for navigation items
                        ReadifyScreens.forEach { readifyDestination ->
                            NavigationBarItem(
                                selected = false,
                                onClick = {
                                    selectedOption = true
                                    navController.navigateSingleTopTo(readifyDestination.route)

                                },
                                icon = {
                                    val iconTint = if (selectedOption) {
                                        Color.Green
                                    } else {
                                        Color.White
                                    }

                                    Icon(
                                        readifyDestination.icon,
                                        contentDescription = "${readifyDestination.route} icon",
                                        tint = iconTint
                                    )
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            // Existing code for navigation
            NavHost(
                navController = navController,
                startDestination = "bookCollection",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = BookCollection.route) {
                    BookCollection(modifier, navController)
                }
                composable(route = Settings.route) {
                    // Pass selectedTheme and onThemeChange to the Settings composable
                    Settings(
                        selectedTheme = selectedTheme,
                        onThemeChange = onThemeChange,
                        userfontSize = userfontSize,
                        onFontChange = onFontChange,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(route = Library.route) {
                    Library(navController)
                }
                composable(route = Details.route) {
                    Details(focusedBook, navController)
                }
                composable(route = "login") {
                    LoginScreen(navController = navController)
                }
                composable(route = "signUp") {
                    SignUpScreen(navController = navController)
                }
                composable(route = BookReading.route) {

                }
            }
        }
    }
}




fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }

            launchSingleTop = true
            restoreState = true

    }


/**
 * Composable that displays a list item containing a question along with an answer, used for the about us page
 * @param book Contains the data that populates the list item.
 * @param modifier Modifiers to set for this composable.
 */



/**
 * Preview function for MyApp composable.
 */
@Preview
@Composable
fun MyAppPreview() {
    var selectedTheme by remember { mutableStateOf(Theme.Light) }
    var fontSize by remember { mutableStateOf(16f) }
        MyApp(
            modifier = Modifier.fillMaxSize(),
            selectedTheme = selectedTheme,
            onThemeChange = { newTheme ->
                selectedTheme = newTheme
            },
            userfontSize = fontSize,
            onFontChange = {
                    userfontSize ->
                fontSize = userfontSize
            }
        )
    }




/**
 * Composable function for the top app bar.
 * @param modifier Modifier for the composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTopAppBar(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
    ) {
        CenterAlignedTopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.logo_size))
                                .padding(dimensionResource(id = R.dimen.padding_small)),
                            painter = painterResource(R.drawable.bookcraftlogo),
                            contentDescription = null,
                        )
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.headlineLarge,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.LightGray
                        )
                    }
                }
            }
        )
    }
}


