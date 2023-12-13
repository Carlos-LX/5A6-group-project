package com.example.bookcraftapplication.ui.reviews

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.codelab.basics.R
import com.example.bookcraft.data.Book
import com.example.bookcraftapplication.data.Review
import com.example.bookcraftapplication.ui.books.BookIcon
import com.example.bookcraftapplication.ui.books.BookInformation

@Composable
fun ReviewItem(
    review: Review,
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
                ReviewInformation(review.rating, review.title, review.description)
            }
        }
    }
}

@Composable
fun ReviewInformation(
    rating: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = "Rating: $rating",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}