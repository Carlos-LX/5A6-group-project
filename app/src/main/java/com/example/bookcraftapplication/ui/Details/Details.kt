package com.example.bookcraftapplication.ui.Details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.codelab.basics.R
import com.example.bookcraftapplication.ui.theme.md_theme_dark_tertiary
import com.example.bookcraft.data.Book
import com.example.bookcraft.data.focusedBook
import com.example.bookcraft.data.libraryBooks
import com.example.bookcraftapplication.data.Review
import com.example.bookcraftapplication.data.reviews
import com.example.bookcraftapplication.navigateSingleTopTo
import com.example.bookcraftapplication.ui.books.BookItem
import com.example.bookcraftapplication.ui.reviews.ReviewItem

@Composable
fun BookItemCard(bookItem: Book) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = bookItem.imageResourceId),
            contentDescription = null,
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = bookItem.name + " " + bookItem.author, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(4.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = bookItem.description, style = MaterialTheme.typography.headlineSmall)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Reviews", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun BookPage(bookItem: Book) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BookItemCard(bookItem = bookItem)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun Details(book: Book) {
    LazyColumn {
        item {
            BookPage(book)
        }

        items(reviews) { review ->
            Reviews(modifier = Modifier, review)
        }
    }

    // Add button below LazyColumn
    Button(
        onClick = {
            libraryBooks = libraryBooks.plus(book)
            println("Book added. New book list:")
            println(libraryBooks.last())
        },
        modifier = Modifier
            .width(220.dp)
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(md_theme_dark_tertiary)
    ) {
        Text(text = "ADD TO FAVORITES")
    }
}

@Composable
fun Reviews(modifier: Modifier, review: Review) {
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
            ReviewItem(review = review)
        }
    }
}
