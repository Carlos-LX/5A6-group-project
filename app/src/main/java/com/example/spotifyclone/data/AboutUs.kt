package com.example.spotifyclone.data

import androidx.annotation.DrawableRes
import com.codelab.basics.R


/**
 * A data class to represent the information presented in the question card
 */
data class About(
    val question: String, // Changed to String
    val answer: String
)

val questions = listOf(
    About("Who are we?", "We are 3 students currently enrolled in the Computer Science Program."),
    About("What are you developing?", answer = "In short, an application which allows you to browse and discover new books."),
    About("What year are you guys in?", answer = "Third year."),
    About("What school are you going to?", answer = "John Abbott College."),
    About("Why are you making this app?", answer = "This is part of our course outline for Application Development 2." ),
    About("How long will it take to complete?", answer = "We aim to complete this project by December."),



    )