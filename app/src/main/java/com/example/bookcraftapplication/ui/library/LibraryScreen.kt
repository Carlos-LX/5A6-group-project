package com.example.bookcraftapplication.ui.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bookcraft.data.focusedBook
import com.example.bookcraft.data.storeBooks
import com.example.bookcraftapplication.Details
import com.example.bookcraftapplication.LocalNavController
import com.example.bookcraftapplication.R
import com.example.bookcraftapplication.ui.books.BookItem

/**
 * Screen that the user sees when selecting the library icon. Allows them to select a book to add a review or add to favorites.
 */
@Composable
fun Library() {
    val (storeBookList, setStoreBookList) = remember { mutableStateOf(storeBooks) }
    val navController = LocalNavController.current

    Scaffold { it ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(it),
        ) {
            Text(
                text = "LIBRARY",
                style =
                    MaterialTheme.typography.titleLarge
                        .copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Default),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                textAlign = TextAlign.Center,
            )

            // Display individual book items with text below
            LazyVerticalGrid(GridCells.Fixed(3)) {
                items(storeBookList) { book ->
                    Card(
                        modifier =
                            Modifier
                                .padding(dimensionResource(R.dimen.padding_small))
                                .height(250.dp) // Adjust the height as needed
                                .clickable {
                                    focusedBook = book
                                    navController.navigate(Details.route)
                                }
                                .semantics(
                                    mergeDescendants = true,
                                ) { onClick(label = "Click to view ${book.name} details", action = null) },
                    ) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                        ) {
                            BookItem(
                                book = book,
                                false,
                                modifier =
                                    Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(8.dp)) // Adjust spacing as needed
                            Text(
                                text = book.name, // or any other information
                                textAlign = TextAlign.Center,
                                modifier =
                                    Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                            )
                        }
                    }
                }
            }
        }
    }
}
