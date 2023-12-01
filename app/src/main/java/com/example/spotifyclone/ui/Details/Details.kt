package com.example.spotifyclone.ui.Details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.spotifyclone.ui.theme.md_theme_dark_errorContainer
import com.example.spotifyclone.ui.theme.md_theme_dark_onSecondary
import com.example.spotifyclone.ui.theme.md_theme_dark_tertiary
import com.example.spotifyclone.ui.theme.md_theme_dark_tertiaryContainer
import com.example.spotifyclone.ui.theme.md_theme_light_onPrimaryContainer
import com.example.spotifyclone.ui.theme.md_theme_light_onTertiaryContainer
import com.example.woof.data.Book
import com.example.woof.data.libraryBooks
import com.example.woof.data.storeBooks

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
    }

    // Add button below LazyColumn
    Button(
        onClick = {
            libraryBooks = libraryBooks.plus(book)
            println("Book added. New book list:")
            println(libraryBooks.last())
        },
        modifier = Modifier
            .width(170.dp)
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(md_theme_dark_tertiary)
    ) {
        Text(text = "ADD BOOK")
    }
}