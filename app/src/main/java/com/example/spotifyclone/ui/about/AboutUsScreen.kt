package com.example.spotifyclone.ui.about

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelab.basics.R
import com.example.spotifyclone.data.About
import com.example.spotifyclone.data.questions

@Composable
fun AboutUs(modifier: Modifier = Modifier) {

    val (textList) = remember { mutableStateOf(questions) }

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(modifier = modifier.fillMaxSize()) {

            items(textList) { question ->
                // Displays individual book items
                TextItem(
                    text = question,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}



/**
 * Composable that displays a list item containing a question along with an answer, used for the about us page
 * @param book Contains the data that populates the list item.
 * @param modifier Modifiers to set for this composable.
 */
@Composable
fun TextItem(
    text: About,
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
                Text(text = text.question, fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.padding(
                    dimensionResource(R.dimen.padding_small)
                ))
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