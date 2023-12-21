package com.example.bookcraftapplication.ui.books

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.bookcraft.data.Book
import com.example.bookcraftapplication.R

/**
 * Composable function for adding a new book item.
 * @param onAddBookClick Callback when the "Add" button is clicked.
 */

/**
 * Composable function for adding a new book item.
 * @param onAddBookClick Callback when the "Add" button is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookItem(onAddBookClick: (Book) -> Unit) {
    // State variables for managing user input
    var isExpanded by remember { mutableStateOf(false) }
    var bookName by remember { mutableStateOf("") }
    var bookAuthor by remember { mutableStateOf("") }
    var releaseDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(dimensionResource(R.dimen.padding_small))
                .animateContentSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                // Display the "Add Book" text
                Text(
                    text = "Add Book",
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                )
            }

            if (isExpanded) {
                // User input fields
                TextField(
                    value = bookName,
                    onValueChange = { bookName = it },
                    label = { Text("Book Name") },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_small)),
                )

                TextField(
                    value = releaseDate,
                    onValueChange = { releaseDate = it },
                    label = { Text("Release Date") },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_small)),
                )

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_small)),
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
                    modifier =
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(dimensionResource(R.dimen.padding_small)),
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
private fun createBook(
    bookName: String,
    bookAuthor: String,
    releaseDateStr: String,
    description: String,
): Book? {
    val releaseDate = releaseDateStr.toIntOrNull()
    if (releaseDate == null || bookName.isEmpty() || description.isEmpty()) {
        // Handles validation errors here
        return null
    }

    // Creates and return a new Book object
    return Book(R.drawable.ph, bookName, bookAuthor, releaseDate, description)
}

/**
 * Composable that displays a list item containing a book icon and its information.
 * @param book Contains the data that populates the list item.
 * @param modifier Modifiers to set for this composable.
 */
@Composable
fun BookItem(
    book: Book,
    isExpanded: Boolean,
    modifier: Modifier = Modifier,
) {
    // State variable for expanding/collapsing book description

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
                .animateContentSize(), // Animates the content size change
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                // Displays the book icon
                BookIcon(book.imageResourceId)
                // Displays book information
                BookInformation(book.name, book.author, book.releaseDate)
            }

            // Displays the book.description when expanded
            if (isExpanded) {
                Text(
                    text = book.description,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                )
            }
        }
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
    modifier: Modifier = Modifier,
) {
    Image(
        modifier =
            modifier
                .size(
                    width = dimensionResource(R.dimen.width),
                    height = dimensionResource(R.dimen.height),
                ) // Sets the size of the image
                .padding(dimensionResource(R.dimen.padding_small))
                .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        painter = painterResource(bookIcon),
        contentDescription = null,
    )
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
    bookAuthor: String,
    releaseDate: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = bookName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small)),
        )
        Text(
            text = "Released in: $releaseDate",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
