package com.example.bookcraftapplication.ui.Details

import android.widget.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.codelab.basics.R
import com.example.bookcraftapplication.ui.theme.md_theme_dark_tertiary
import com.example.bookcraft.data.Book
import com.example.bookcraft.data.focusedBook
import com.example.bookcraft.data.libraryBooks
import com.example.bookcraftapplication.Library
import com.example.bookcraftapplication.LocalNavController
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
    }
}

@Composable
fun BookPage(bookItem: Book) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        BookItemCard(bookItem = bookItem)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun Details(book: Book) {
    val navController = LocalNavController.current
    var userRating by remember { mutableStateOf(0f) }
    var reviewTitle by remember { mutableStateOf("") }
    var reviewDescription by remember { mutableStateOf("") }
    var snackbarVisible by remember { mutableStateOf(false) }
    var triggerScroll by remember { mutableStateOf(0) }

    LazyColumn {
        item {
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Go back")
            }
        }

        item {
            BookPage(book)
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Reviews", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Add Your Review",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Slider(
                    value = userRating,
                    onValueChange = {
                        userRating = it
                    },
                    valueRange = 0f..5f,
                    steps = 4,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = reviewTitle,
                    onValueChange = {
                        reviewTitle = it
                    },
                    label = { Text("Review Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = androidx.compose.ui.text.input.ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {

                        }
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = reviewDescription,
                    onValueChange = {
                        reviewDescription = it
                    },
                    label = { Text("Review Description") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = androidx.compose.ui.text.input.ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            submitReview(
                                Review("", userRating, reviewTitle, reviewDescription)
                            )

                            userRating = 0f
                            reviewTitle = ""
                            reviewDescription = ""

                            triggerScroll += 1
                        }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        submitReview(Review("", userRating, reviewTitle, reviewDescription))

                        userRating = 0f
                        reviewTitle = ""
                        reviewDescription = ""

                        triggerScroll += 1
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit Review")
                }
            }
        }

        item(key = triggerScroll) {}

        item {
            // Snackbar
            Button(
                onClick = {
                    addToFavorites(book)
                    snackbarVisible = true
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(md_theme_dark_tertiary)
            ) {
                Text(text = "Add To Favorites")
            }

            if (snackbarVisible) {
                Snackbar(
                    action = {
                        Button(
                            onClick = {
                                snackbarVisible = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Black
                            )
                        ) {
                            Text(text = "Dismiss")
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(text = "Book added to favorites!", color = Color.White)
                }
            }
        }

        items(reviews) { review ->
            Reviews(modifier = Modifier, review)
        }
    }
}

fun submitReview(review: Review) {
    reviews = listOf(review) + reviews
}

fun addToFavorites(book: Book) {
    libraryBooks = listOf(book) + libraryBooks
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
