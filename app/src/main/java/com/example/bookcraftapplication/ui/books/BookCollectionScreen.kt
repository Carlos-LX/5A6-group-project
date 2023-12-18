package com.example.bookcraftapplication.ui.books

import androidx.compose.foundation.clickable
import com.example.bookcraft.data.focusedBook
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bookcraftapplication.R
import com.example.bookcraftapplication.Details
import com.example.bookcraftapplication.navigateSingleTopTo
import com.example.bookcraft.data.Book

import com.example.bookcraft.data.libraryBooks

@Composable
fun BookCollection(modifier: Modifier = Modifier, navController: NavHostController) {
    val (bookList, setBookList) = remember { mutableStateOf(libraryBooks) }
    val (selectedBook, setSelectedBook) = remember { mutableStateOf<Book?>(null) }
    val (showAlertDialog, setShowAlertDialog) = remember { mutableStateOf(false) }


    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = "FAVORITES",
                style = MaterialTheme.typography.titleLarge
                    .copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Default),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                textAlign = TextAlign.Center
            )
        }
        items(bookList) { book ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    // Display book item details
                    BookItem(book = book)

                    // Read Icon in the Upper Left Corner
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = "Read",
                        tint = Color.Green,
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable {
                                focusedBook = book
                                navController.navigateSingleTopTo(Details.route)
                            }
                            .padding(8.dp)
                    )

                    // Delete Icon in the Upper Right Corner
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red,
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable {
                                setSelectedBook(book)
                                setShowAlertDialog(true)
                            }
                            .padding(8.dp)
                    )
                }
            }
        }
    }

    // Alert Dialog for Confirmation
    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = {
                // Handle dismiss if needed
                setShowAlertDialog(false)
            },
            title = {
                Text(
                    text = "Delete Book",
                    textAlign = TextAlign.Right,
                    color = Color.White // Change to your desired lighter color
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete ${selectedBook?.name}?",
                    color = Color.White // Change to your desired lighter color
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Handle the deletion
                        setBookList(bookList.filter { it != selectedBook })
                        setShowAlertDialog(false)
                    }
                ) {
                    Text(
                        text = "Yes",
                        color = Color.White // Change to your desired lighter color
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Handle cancel logic here
                        setShowAlertDialog(false)
                    }
                ) {
                    Text(
                        text = "Cancel",
                        color = Color.White // Change to your desired lighter color
                    )
                }
            }
        )
    }
}

