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
import com.example.woof.data.Book
import com.example.woof.data.books

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
                    NavigationBarItem(selected = false, onClick = { navController.navigateSingleTopTo("settings")}, icon = {
                        Icon(
                            Icons.Rounded.Settings,
                            contentDescription = "Home Icon",
                            tint = Color.White
                        )
                    })
                    NavigationBarItem(selected = false, onClick = {  navController.navigateSingleTopTo("bookCollection")}, icon = {
                        Icon(
                            Icons.Rounded.Book,
                            contentDescription = "Book icon",
                            tint = Color.White
                        )
                    })
                    NavigationBarItem(selected = false, onClick = {  navController.navigateSingleTopTo("about")}, icon = {
                        Icon(
                            Icons.Rounded.Person,
                            contentDescription = "About us icon",
                            tint = Color.White

                        )
                    })
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


@Composable
fun BookCollection(modifier: Modifier = Modifier) {

    val (bookList, setBookList) = remember { mutableStateOf(books) }
    LazyColumn(modifier = modifier) {
        item {
            // Displays the "Add Book" card
            AddBookItem(
                onAddBookClick = { newBook ->
                    setBookList(bookList + newBook)
                }
            )
        }
        items(bookList) { book ->
            // Displays individual book items
            BookItem(
                book = book,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun Settings(modifier: Modifier = Modifier) {
    var sliderValue by remember { mutableStateOf(0f) }
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column() {
            selectTheme()
            Slider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                },
                valueRange = 0f..100f, // Define the range for the slider
                steps = 100 // Number of steps within the range
            )
        }

    }
}

@Composable
fun selectTheme(modifier: Modifier = Modifier) {
    val radioOptions = listOf(Theme.Light, Theme.Dark)
    var (selectedTheme, onThemeChange) = remember { mutableStateOf(Theme.Light ) }
    Column {
        radioOptions.forEach { theme ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (theme == selectedTheme),
                        onClick = {
                            onThemeChange(theme)
                        }
                    )
                    .padding(horizontal = 16.dp)
            ) {
                RadioButton(
                    selected = (theme == selectedTheme),
                    onClick = { onThemeChange(theme) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = theme.name, modifier = Modifier.padding(start = 16.dp))

            }
        }
    }
}


/**
 * Composable function for adding a new book item.
 * @param onAddBookClick Callback when the "Add" button is clicked.
 */
@Composable
fun AddBookItem(
    onAddBookClick: (Book) -> Unit,
) {
    // State variables for managing user input
    var isExpanded by remember { mutableStateOf(false) }
    var bookName by remember { mutableStateOf("") }
    var releaseDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .padding(dimensionResource(R.dimen.padding_small))
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Display the "Add Book" text
                Text(
                    text = "Add Book",
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }

            if (isExpanded) {
                // User input fields
                TextField(
                    value = bookName,
                    onValueChange = { bookName = it },
                    label = { Text("Book Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_small))
                )

                TextField(
                    value = releaseDate,
                    onValueChange = { releaseDate = it },
                    label = { Text("Release Date") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_small))
                )

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
                        val newBook = createBook(bookName, releaseDate, description)
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
    }
}

/**
 * Create a new Book object based on user input.
 * @param bookName The name of the book.
 * @param releaseDateStr The release date of the book as a string.
 * @param description The description of the book.
 * @return The created Book object or null if input is invalid.
 */
private fun createBook(bookName: String, releaseDateStr: String, description: String): Book? {
    val releaseDate = releaseDateStr.toIntOrNull()
    if (releaseDate == null || bookName.isEmpty() || description.isEmpty()) {
        // Handles validation errors here
        return null
    }

    // Creates and return a new Book object
    return Book(R.drawable.ph, bookName, releaseDate, description)
}

/**
 * Composable that displays a list item containing a book icon and its information.
 * @param book Contains the data that populates the list item.
 * @param modifier Modifiers to set for this composable.
 */
@Composable
fun BookItem(
    book: Book,
    modifier: Modifier = Modifier
) {
    // State variable for expanding/collapsing book description
    var isExpanded by remember { mutableStateOf(false) }

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
                BookInformation(book.name, book.releaseDate)
            }

            // Displays the book.description when expanded
            if (isExpanded) {
                Text(
                    text = book.description,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}


/**
 * Composable that displays a list item containing a question along with an answer, used for the about us page
 * @param book Contains the data that populates the list item.
 * @param modifier Modifiers to set for this composable.
 */
@Composable
fun TextItem(
    text: About,
    modifier: Modifier = Modifier
) {
    // State variable for expanding/collapsing book description
    var isExpanded by remember { mutableStateOf(false) }

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
                Text(text = text.question, fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
            }

            // Displays the book.description when expanded
            if (isExpanded) {
                Spacer(modifier = Modifier)
                Text(
                    text = text.answer,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}


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
 * Composable function for displaying a book icon.
 * @param bookIcon The resource ID of the book icon.
 * @param modifier Modifier for the composable.
 */
@Composable
fun BookIcon(
    @DrawableRes bookIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(
                width = dimensionResource(R.dimen.width),
                height = dimensionResource(R.dimen.height)
            ) // Sets the size of the image
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        painter = painterResource(bookIcon),
        contentDescription = null
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

@Composable
fun AboutUs(modifier: Modifier = Modifier) {

    val (textList) = remember { mutableStateOf(questions) }

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(modifier = modifier.fillMaxSize()) {

            items(textList) { question ->
                // Displays individual book items
                TextItem(
                    text = question,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}