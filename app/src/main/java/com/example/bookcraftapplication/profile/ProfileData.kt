package com.example.bookcraftapplication.profile

import com.example.bookcraft.data.Book

/**
 * Represents the current user after login
 */
data class ProfileData(
    val userId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val favoriteBooks: List<Book> = emptyList(),
)
