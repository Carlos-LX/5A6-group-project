package com.example.bookcraftapplication.ui.reviews

import androidx.compose.animation.animateContentSize
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
import com.example.bookcraftapplication.R
import com.example.bookcraftapplication.data.Review

/**
 * Composable which displays a review for a book.
 */
@Composable
fun ReviewItem(
    review: Review,
    modifier: Modifier = Modifier,
) {
    // State variable for expanding/collapsing book description
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
                .animateContentSize(), // Animates the content size change
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                ReviewInformation(review.rating, review.title, review.description)
            }
        }
    }
}

@Composable
fun ReviewInformation(
    rating: Float,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small)),
        )
        Text(
            text = "Rating: $rating",
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
