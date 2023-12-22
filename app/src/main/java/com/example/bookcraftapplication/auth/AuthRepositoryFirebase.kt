package com.example.bookcraftapplication.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

/*
https://firebase.google.com/docs/auth
 */
class AuthRepositoryFirebase(private val auth: FirebaseAuth) : AuthRepository {
    private val currentUserStateFlow = MutableStateFlow(auth.currentUser?.toUser())

    init {
        auth.addAuthStateListener { firebaseAuth ->
            currentUserStateFlow.value = firebaseAuth.currentUser?.toUser()
        }
    }

    override fun currentUser(): StateFlow<User?> {
        return currentUserStateFlow
    }

    /**
     * Creates a user in the database with the provided email and password
     */
    override suspend fun signUp(
        email: String,
        password: String,
    ): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }
    /**
     * Attempts to sign in to a user with the provided email and password
     */
    override suspend fun signIn(
        email: String,
        password: String,
    ): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true // Authentication successful
        } catch (e: Exception) {
            println("Exception during signIn: ${e.message}")
            false // Authentication failed
        }
    }
    /**
     * Signs out the current user
     */
    override fun signOut(): Boolean {
        auth.signOut()
        return true
    }

    override suspend fun delete(): Boolean {
        return if (auth.currentUser != null) {
            auth.currentUser!!.delete()
            true
        } else {
            false
        }
    }

    /** Convert from FirebaseUser to User */
    private fun FirebaseUser?.toUser(): User? {
        return this?.let {
            if (it.email == null) {
                null
            } else {
                User(
                    email = it.email!!,
                )
            }
        }
    }
}
