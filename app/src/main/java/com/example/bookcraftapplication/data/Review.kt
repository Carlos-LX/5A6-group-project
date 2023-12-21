package com.example.bookcraftapplication.data

import com.example.bookcraftapplication.R
import com.example.bookcraft.data.Book

data class Review(
    val rating: Float,
    val title: String,
    val description: String
) {

}


var reviews = mutableListOf<Review>()
