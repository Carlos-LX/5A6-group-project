package com.example.bookcraftapplication.ui.books

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bookcraft.data.Book
import com.example.bookcraft.data.focusedBook
import com.example.bookcraft.data.libraryBooks
import com.example.bookcraftapplication.Details
import com.example.bookcraftapplication.LocalNavController
import com.example.bookcraftapplication.R
import com.example.bookcraftapplication.data.userEmail
import com.example.bookcraftapplication.navigateSingleTopTo
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BookCollection(db: FirebaseFirestore, modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val (bookList, setBookList) = remember { mutableStateOf(libraryBooks) }
    val (selectedBook, setSelectedBook) = remember { mutableStateOf<Book?>(null) }
    val (showAlertDialog, setShowAlertDialog) = remember { mutableStateOf(false) }

    // State to store user favorites obtained from Firestore
    val (favorites, setFavorites) = remember { mutableStateOf<List<String>>(emptyList()) }

    // Fetch favorites from Firestore
    LaunchedEffect(userEmail) {
        db.collection("user-profile")
            .whereEqualTo("Email", userEmail)
            .get()
            .addOnSuccessListener { documents ->
                val newFavorites = mutableListOf<String>()
                for (document in documents) {
                    val favoritesList = document["Favorites"] as? List<String>
                    if (favoritesList != null) {
                        newFavorites.addAll(favoritesList)
                    }
                }
                setFavorites(newFavorites)
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
    }

    val filteredBookList = bookList.filter { book ->
        favorites.contains(book.name)
    }

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
        items(filteredBookList) { book ->
            // State variable for expanding/collapsing book description
            var isExpanded by remember { mutableStateOf(false) }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
                    .clickable { isExpanded = !isExpanded }
                    .semantics(mergeDescendants = true) { onClick(label = "Click to see ${book.name} details", action = null) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    // Display book item details
                    BookItem(book = book, isExpanded, modifier = Modifier.fillMaxSize())

                    // Read Icon in the Upper Left Corner
                    Row(modifier = Modifier.align(Alignment.End)) {
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .semantics(mergeDescendants = true) {}
                                .padding(10.dp),
                            onClick = {
                                focusedBook = book
                                navController.navigateSingleTopTo(Details.route)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Book,
                                contentDescription = "View $book reviews",
                                tint = Color.Green,
                                modifier = Modifier
                            )
                        }
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(10.dp),
                            onClick = {
                                setSelectedBook(book)
                                setShowAlertDialog(true)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove $book from collection",
                                tint = Color.Red,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }
    }

    // Alert Dialog for Confirmation
    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = {
                setShowAlertDialog(false)
            },
            title = {
                Text(
                    text = "Delete Book",
                    textAlign = TextAlign.Right,
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete ${selectedBook?.name}?",
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        deleteBookFromFirestore(selectedBook, db)
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

private fun deleteBookFromFirestore(book: Book?, db: FirebaseFirestore) {
    if (book != null) {
        // Assuming "user-profile" is your Firestore collection name
        db.collection("user-profile")
            .whereEqualTo("Email", userEmail)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val favoritesList = document["Favorites"] as? MutableList<String>
                    if (favoritesList != null) {
                        if (favoritesList.contains(book.name)) {
                            favoritesList.remove(book.name)
                            document.reference.update("Favorites", favoritesList)
                                .addOnSuccessListener {
                                    // Handle success
                                    println("Book deleted successfully from Favorites array")
                                    // Break out of the loop after deleting the book
                                    return@addOnSuccessListener
                                }
                                .addOnFailureListener { e ->
                                    // Handle failure
                                    println("Error updating document: $e")
                                }
                        } else {
                            println("Book not found in Favorites array")
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                // Handle failure
                println("Error getting documents: $e")
            }
    }
}

