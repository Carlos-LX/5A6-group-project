package com.example.bookcraftapplication.ui.aboutUs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bookcraftapplication.R
import com.example.bookcraft.data.Book
import com.example.bookcraftapplication.Login
import com.example.bookcraftapplication.SignUp
import com.example.bookcraftapplication.navigateSingleTopTo

@Composable
fun AboutUs(navHostController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
                color = Color.LightGray,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        item {
            // Logo Image
            Image(
                painter = painterResource(id = R.drawable.bookcraftlogo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .clip(shape = MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }

        item {
            // Brief Explanation
            Text(
                text = "Our amazing team is dedicated to creating a centralized hub for book lovers to share their thoughts on " +
                        "their most recent reads. This application allows you to bookmark books that interest you, and leave reviews " +
                        "for books you already read.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        item {
            // Button
            Button(
                onClick = { navHostController.navigateSingleTopTo(Login.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text("SIGN IN")
            }
        }
    }
}