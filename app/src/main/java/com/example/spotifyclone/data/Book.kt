/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.woof.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.codelab.basics.R

/**
 * A data class to represent the information presented in the book card
 */
data class Book(
    @DrawableRes val imageResourceId: Int,
    val name: String, // Changed to String
    val releaseDate: Int,
    val description: String
)

val books = listOf(
    Book(R.drawable.tkamb, "To Kill a Mockingbird by Harper Lee", 1960, "A poignant exploration of racial prejudice and moral growth in the American South during the 1930s."),
    Book(R.drawable.nef, "1984 by George Orwell", 1949, "A chilling dystopian masterpiece that delves into themes of totalitarianism, surveillance, and the manipulation of truth."),
    Book(R.drawable.tgg, "The Great Gatsby by F. Scott Fitzgerald", 1925, "Set against the backdrop of the extravagant Jazz Age, a classic tale of love, wealth, and disillusionment."),
    Book(R.drawable.pap, "Pride and Prejudice by Jane Austen", 1813, "A timeless romance novel that deftly satirizes societal norms and class distinctions in early 19th-century England."),
    Book(R.drawable.citr, "The Catcher in the Rye by J.D. Salinger", 1951, "Follows the journey of the disillusioned teenager Holden Caulfield as he navigates the challenges of adolescence in New York City."),
    Book(R.drawable.lotr, "The Lord of the Rings by J.R.R. Tolkien", 1954, "An epic fantasy trilogy set in the richly imagined world of Middle-earth."),
    Book(R.drawable.hp, "Harry Potter and the Sorcerer's Stone by J.K. Rowling", 1998, "The magical world of Harry Potter comes to life in this enchanting tale of a young wizard discovering his true heritage."),
    Book(R.drawable.hobbit, "The Hobbit by J.R.R. Tolkien", 1937, "A delightful adventure filled with dwarves, dragons, and one unassuming hobbit named Bilbo Baggins."),
    Book(R.drawable.bnw, "Brave New World by Aldous Huxley", 1932, "Paints a chilling portrait of a highly controlled society where individuality is sacrificed for societal stability and happiness."),
    Book(R.drawable.ph, "Book 10", 0, "Book 10 description") // Example of a book with a custom name
)