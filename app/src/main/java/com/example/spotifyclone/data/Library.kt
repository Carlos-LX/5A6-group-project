package com.example.spotifyclone.data


/**
 * A data class to represent the information presented in the question card
 */
data class Library(
    val question: String, // Changed to String
    val answer: String
)

val questions = listOf(
    Library("Who are we?", "We are 3 students currently enrolled in the Computer Science Program."),
    Library("What are you developing?", answer = "In short, an application which allows you to browse and discover new books."),
    Library("What year are you guys in?", answer = "Third year."),
    Library("What school are you going to?", answer = "John Abbott College."),
    Library("Why are you making this app?", answer = "This is part of our course outline for Application Development 2." ),
    Library("How long will it take to complete?", answer = "We aim to complete this project by December."),)