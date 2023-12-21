package com.example.bookcraftapplication.auth

import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    // Return a StateFlow so that the composable can always update when
    //   the current authorized user status changes for any reason
    fun currentUser(): StateFlow<User?>

    suspend fun signUp(
        email: String,
        password: String,
    ): Boolean

    suspend fun signIn(
        email: String,
        password: String,
    ): Boolean

    fun signOut(): Boolean

    suspend fun delete(): Boolean
}
