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

package com.example.spotifyclone

// The vast majority of source code I got was from the kotlin bootcamp with some help using chat gpt.
// I mainly used chat gpt to aid with printing an actual bookItem when the add button is pressed, I
// overshot with the state and user input criteria and I struggled to figure it out on my own
import android.app.LauncherActivity.ListItem
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codelab.basics.R
import com.example.spotifyclone.data.About
import com.example.spotifyclone.data.questions
import com.example.spotifyclone.ui.theme.SpotifyCloneTheme
import com.example.spotifyclone.ui.books.BookCollection
import com.example.spotifyclone.ui.settings.Settings
import com.example.spotifyclone.ui.about.AboutUs
import com.example.woof.data.Book
import com.example.woof.data.books
import java.util.Objects

enum class Theme {
    Light, Dark
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            SpotifyCloneTheme {
                // Initialize the main app UI
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}



/**
 * The main composable function that defines the app's UI.
 * @param modifier Modifier for the Surface composable.
 */
@Composable
fun MyApp(modifier: Modifier = Modifier) {

    //TODO: move the screens to the appropriate place

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier,
        topBar = { BookTopAppBar() },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier,

                ) {
                Row {
                    ReadifyScreens.forEach { readifyDestination ->

                        NavigationBarItem(selected = false, onClick = { navController.navigateSingleTopTo(readifyDestination.route) }, icon = {
                            Icon(
                                readifyDestination.icon,
                                contentDescription = "${readifyDestination.route} icon",
                                tint = Color.White
                            )
                        })
                    }
                }
            }
        }
    ) {innerPadding ->
        NavHost(navController = navController,
            startDestination = "bookCollection",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = "bookCollection") {
                BookCollection()
            }
            composable(route = "settings") {
                Settings()
            }
            composable(route = "about") {
                AboutUs()
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
 * Preview function for MyApp composable.
 */
@Preview
@Composable
fun MyAppPreview() {
    SpotifyCloneTheme {
        MyApp(Modifier.fillMaxSize())
    }
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
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.headlineLarge,
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Black,
                            color = Color.LightGray
                        )
                    }
                }
            }
        )
    }
}

