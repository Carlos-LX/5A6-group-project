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
import androidx.compose.material3.Surface
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
import com.codelab.basics.R
import com.example.spotifyclone.data.Library
import com.example.spotifyclone.ui.books.BookItem
import com.example.woof.data.books

@OptIn(ExperimentalMaterial3Api::class)
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
                    text = "STORE",
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



/**
 * Composable that displays a list item containing the book title and description
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