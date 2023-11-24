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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Store
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codelab.basics.R
import com.example.spotifyclone.data.Library
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
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_small))
                )

                // Add button
                Button(
                    onClick = {
                        // Validates user input and create a new Book object
                        val newBook = createBook(bookName, bookAuthor, releaseDate, description)
                        if (newBook != null) {
                            // Add the new book to the list
                            onAddBookClick(newBook)

                            // Clears input fields
                            bookName = ""
                            releaseDate = ""
                            description = ""
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(dimensionResource(R.dimen.padding_small))
                ) {
                    Text("Add")
                }
            }
        }
        launchSingleTop = true
        restoreState = true
    }

/**
 * Create a new Book object based on user input.
 * @param bookName The name of the book.
 * @param releaseDateStr The release date of the book as a string.
 * @param description The description of the book.
 * @return The created Book object or null if input is invalid.
 */
private fun createBook(bookName: String, bookAuthor: String, releaseDateStr: String, description: String): Book? {
    val releaseDate = releaseDateStr.toIntOrNull()
    if (releaseDate == null || bookName.isEmpty() || description.isEmpty()) {
        // Handles validation errors here
        return null
    }

    // Creates and return a new Book object
    return Book(R.drawable.ph, bookName, bookAuthor, releaseDate, description)
}


    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded } // Toggle isExpanded on click
            .padding(dimensionResource(R.dimen.padding_small))
            .animateContentSize() // Animates the content size change
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Displays the book icon
                BookIcon(book.imageResourceId)
                // Displays book information
                BookInformation(book.name, book.author, book.releaseDate)
            }




/**
 * Composable that displays a list item containing a question along with an answer, used for the about us page
 * @param book Contains the data that populates the list item.
 * @param modifier Modifiers to set for this composable.
 */
@Composable
fun TextItem(
    text: Library,
    modifier: Modifier = Modifier
) {
    // State variable for expanding/collapsing book description
    var isExpanded by remember { mutableStateOf(false) }




/**
 * Composable function for displaying book information.
 * @param bookName The name of the book.
 * @param releaseDate The release date of the book.
 * @param modifier Modifier for the composable.
 * I commented
 */
@Composable
fun BookInformation(
    bookName: String,
    bookAuthor: String,
    releaseDate: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = bookName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = "Released in: $releaseDate",
            style = MaterialTheme.typography.bodyLarge
        )
    }
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier,
        topBar = { BookTopAppBar() },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier,

                ) {
                Row {
                    NavigationBarItem(selected = false, onClick = { currentScreen = 2 }, icon = {
                        Icon(
                            Icons.Rounded.Store,
                            contentDescription = "Library icon",
                            tint = Color.White

                        )
                    })
                    NavigationBarItem(selected = false, onClick = { currentScreen = 1 }, icon = {
                        Icon(
                            Icons.Rounded.Book,
                            contentDescription = "Book icon",
                            tint = Color.White
                        )
                    })
                    NavigationBarItem(selected = false, onClick = { currentScreen = 0 }, icon = {
                        Icon(
                            Icons.Rounded.Settings,
                            contentDescription = "Settings Icon",
                            tint = Color.White
                        )
                    })
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)

        ) {
            if (currentScreen == 0) {
                Settings()
            }
            if (currentScreen == 1) {
                MyApp()
            }
            if (currentScreen == 2) {
                Library()
            }
        }
    }
}


@Composable
fun Library(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        val (bookList, setBookList) = remember { mutableStateOf(books) }

        Scaffold() { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                // Adds a title to the top of the page
                Text(
                    text = "LIBRARY",
                    style = MaterialTheme.typography.titleLarge
                        .copy(fontWeight = FontWeight.Bold
                             ,fontFamily = FontFamily.Default),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )

                LazyVerticalGrid(GridCells.Fixed(3)) {
                    items(bookList) { book ->
                        // Displays individual book items with text below
                        Column {
                            BookItem(
                                book = book,
                                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                                    .height(120.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp)) // Adjust spacing as needed
                            Text(
                                text = "${book.name}", // or any other information
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()

                            )
                        }
                    }
                }
            }
        }
    }
}


