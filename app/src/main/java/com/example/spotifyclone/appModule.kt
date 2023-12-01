package com.example.spotifyclone

import android.content.Context
import com.example.spotifyclone.auth.AuthRepository
import com.example.spotifyclone.auth.AuthRepositoryFirebase
import com.example.spotifyclone.data.ProfileRepository
import com.example.spotifyclone.data.ProfileRepositoryDataStore
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/** This module provides the specific object(s) we will inject */
class AppModule(
    private val appContext: Context
) {
    /* Create appropriate repository (backed by a DataStore) on first use.
       Only one copy will be created during lifetime of the application. */
    val profileRepository : ProfileRepository by lazy {
        ProfileRepositoryDataStore(appContext)
    }
    val authRepository : AuthRepository by lazy {
        AuthRepositoryFirebase(Firebase.auth) // inject Firebase auth
    }
}