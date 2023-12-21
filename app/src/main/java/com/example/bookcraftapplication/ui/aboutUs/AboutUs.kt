package com.example.bookcraftapplication.ui.aboutUs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookcraftapplication.LocalNavController
import com.example.bookcraftapplication.Login
import com.example.bookcraftapplication.R
import com.example.bookcraftapplication.navigateSingleTopTo

@Composable
fun AboutUs() {
    val navController = LocalNavController.current
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        item {
            // Header
            Text(
                text = stringResource(R.string.about_us),
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                modifier =
                    Modifier
                        .fillMaxWidth(),
            )
        }

        item {
            // Logo Image
            Image(
                painter = painterResource(id = R.drawable.bookcraftlogo),
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxWidth(1f)
                        .clip(shape = MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
            )
        }

        item {
            // Brief Explanation
            Text(
                text =
                    "Our amazing team is dedicated to creating a centralized hub for book lovers to share their thoughts on " +
                        "their most recent reads. This application allows you to bookmark books that interest you, and leave reviews " +
                        "for books you already read.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .fillMaxWidth(),
            )
        }

        item {
            // Button
            Button(
                onClick = { navController.navigateSingleTopTo(Login.route) },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
            ) {
                Text("SIGN IN")
            }
        }
    }
}
