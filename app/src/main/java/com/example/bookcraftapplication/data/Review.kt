package com.example.bookcraftapplication.data

import com.codelab.basics.R
import com.example.bookcraft.data.Book

data class Review(
    val bookName: String,
    val rating: Float,
    val title: String,
    val description: String
) {

}


var reviews = listOf(
    Review("Something", 3f, "Crap", "Its like the description says"),
    Review("Something", 3f, "Crap", "Its like the description says"),
    Review("Something", 3f, "Crap", "Its like the description says"),
    Review("Something", 3f, "Crap", "Its like the description says"),
    Review("Something", 3f, "Crap", "Its like the description says"),
    Review("Something", 3f, "Crap", "Its like the description says"),
    Review("Something", 3f, "Crap", "Its like the description says"),
    Review("Something", 3f, "Crap", "Its like the description says")
)
