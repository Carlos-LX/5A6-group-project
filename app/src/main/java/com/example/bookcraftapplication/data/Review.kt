package com.example.bookcraftapplication.data

data class Review(
    val rating: Float,
    val title: String,
    val description: String,
)

var reviews = mutableListOf<Review>()
