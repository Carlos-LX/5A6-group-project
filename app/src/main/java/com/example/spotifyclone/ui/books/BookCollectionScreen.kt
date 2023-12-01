package com.example.spotifyclone.ui.books

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.codelab.basics.R

import com.example.woof.data.books

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