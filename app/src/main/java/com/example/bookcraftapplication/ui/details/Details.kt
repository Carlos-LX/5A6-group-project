package com.example.bookcraftapplication.ui.details

import android.util.Log
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bookcraft.data.Book
import com.example.bookcraftapplication.R
import com.example.bookcraftapplication.data.Review
import com.example.bookcraftapplication.data.userEmail
import com.example.bookcraftapplication.navigateSingleTopTo
import com.example.bookcraftapplication.ui.reviews.ReviewItem
import com.example.bookcraftapplication.ui.theme.md_theme_dark_tertiary
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

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
fun Details(book: Book, db: FirebaseFirestore, navController: NavHostController) {
    var userRating by remember { mutableStateOf(0f) }
    var reviewTitle by remember { mutableStateOf("") }
    var reviewDescription by remember { mutableStateOf("") }
    var snackbarVisible by remember { mutableStateOf(false) }
    var triggerScroll by remember { mutableStateOf(0) }

    // Use state for reviews to trigger recomposition
    var reviewsState by remember { mutableStateOf(mutableListOf<Review>()) }


    LaunchedEffect(book) {
        fetchReviewsForBook(book, db) { newReviews ->
            // Update the reviews list
            reviewsState = newReviews.toMutableList()
            reviewsState.addAll(newReviews)
        }
    }

    LaunchedEffect(triggerScroll) {
        fetchReviewsForBook(book, db) { newReviews ->
            // Update the reviews list
            reviewsState.clear()
            reviewsState.addAll(newReviews)
        }
    }

    LazyColumn {
        item {
            Button(
                onClick = {
                    navController.navigateSingleTopTo(com.example.bookcraftapplication.Library.route)
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
                            submitReview(book.name, Review(userRating, reviewTitle, reviewDescription), db, reviewsState)

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
                        submitReview(
                            book.name,
                            Review(userRating, reviewTitle, reviewDescription),
                            db,
                            reviewsState
                        )

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
            Button(
                onClick = {
                    addToFavorites(book, db)
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
                    Text(text = "Book added to favorites!", color = Color.Black)
                }
            }
        }

        items(reviewsState) { review ->
            Reviews(modifier = Modifier, review)
        }
    }
}

suspend fun fetchReviewsForBook(book: Book, db: FirebaseFirestore, onReviewsFetched: (List<Review>) -> Unit) {
    try {
        val reviewsSnapshot = db.collection("reviews")
            .whereEqualTo("Book", book.name)
            .get()
            .await()

        val newReviews = reviewsSnapshot.documents.mapNotNull { document ->
            val rating = document.getDouble("Rating")?.toFloat() ?: 0f
            val title = document.getString("Title") ?: ""
            val description = document.getString("Description") ?: ""
            Review(rating, title, description)
        }

        // Call the callback function with the new reviews
        onReviewsFetched(newReviews)
    } catch (e: Exception) {
        // Handle the exception
        Log.e("Details", "Error fetching reviews: ${e.message}")
    }
}

fun addToFavorites(book: Book, db: FirebaseFirestore) {
    // Fetch current favorites from Firestore
    val favoritesList = mutableListOf<String>()

    db.collection("user-profile")
        .whereEqualTo("Email", userEmail)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                val currentFavorites = document["Favorites"] as? List<String>
                if (currentFavorites != null) {
                    favoritesList.addAll(currentFavorites)
                }

                // Add the new book to the favorites list
                favoritesList.add(book.name)

                // Update the "Favorites" field in Firestore using the document ID
                val documentId = document.id
                db.collection("user-profile")
                    .document(documentId)
                    .update("Favorites", favoritesList)
                    .addOnSuccessListener {
                        Log.d("Details", "Book added to favorites in Firestore.")
                    }
                    .addOnFailureListener { e ->
                        Log.e("Details", "Error updating favorites: $e")
                    }
            }
        }
        .addOnFailureListener { exception ->
            Log.e("Details", "Error fetching current favorites: $exception")
        }
}

fun submitReview(
    bookName: String,
    review: Review,
    db: FirebaseFirestore,
    reviewsState: MutableList<Review>
) {
    try {
        db.collection("reviews")
            .add(
                mapOf(
                    "Book" to bookName,
                    "Rating" to review.rating,
                    "Title" to review.title,
                    "Description" to review.description
                )
            )
            .addOnSuccessListener { documentReference ->
                // Update the local reviews list if needed
                reviewsState.add(review)
            }
            .addOnFailureListener { e ->
                // Handle the exception
                Log.e("Details", "Error submitting review: $e")
            }
    } catch (e: Exception) {
        // Handle other exceptions if needed
        Log.e("Details", "Error submitting review: $e")
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
            ReviewItem(review = review)
        }
    }
}
