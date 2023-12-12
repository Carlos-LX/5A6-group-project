package com.example.spotifyclone.ui.library

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.codelab.basics.R
import com.example.spotifyclone.Details
import com.example.spotifyclone.ReadifyScreen
import com.example.spotifyclone.data.Library
import com.example.spotifyclone.navigateSingleTopTo
import com.example.spotifyclone.ui.books.AddBookItem
import com.example.spotifyclone.ui.books.BookItem
import com.example.woof.data.focusedBook
import com.example.woof.data.libraryBooks
import com.example.woof.data.storeBooks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Library(navController: NavHostController) {
    val (bookList, setBookList) = remember { mutableStateOf(libraryBooks) }
    val (storeBookList, setStoreBookList) = remember { mutableStateOf(storeBooks) }



    Scaffold() { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // Adds a title to the top of the page
            Text(
                text = "STORE",
                style = MaterialTheme.typography.titleLarge
                    .copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Default),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                textAlign = TextAlign.Center
            )

            // Add the "Add Book" card

//            AddBookItem(
//                onAddBookClick = { newBook ->
//                    setBookList(bookList + newBook)
//                }
//            )

            // Display individual book items with text below
            LazyVerticalGrid(GridCells.Fixed(3)) {
                items(storeBookList) { book ->
                    Card(
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .height(200.dp) // Adjust the height as needed
                            .clickable {
                                focusedBook = book;
                                navController.navigateSingleTopTo("details")
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            BookItem(
                                book = book,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp)) // Adjust spacing as needed
                            Text(
                                text = "${book.name}", // or any other information
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}