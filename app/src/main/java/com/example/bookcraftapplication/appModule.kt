package com.example.bookcraftapplication

import android.content.Context
import com.example.bookcraftapplication.auth.AuthRepository
import com.example.bookcraftapplication.auth.AuthRepositoryFirebase
import com.example.bookcraftapplication.profile.ProfileRepository
import com.example.bookcraftapplication.profile.ProfileRepositoryFirestore
import com.example.bookcraftapplication.userprofile.UserProfileRepository
import com.example.bookcraftapplication.userprofile.UserProfileRepositoryFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

/** This module provides the specific object(s) we will inject */
class AppModule(
    private val appContext: Context,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    /* Create appropriate repository (backed by Firebase) on first use.
    Only one copy will be created during lifetime of the application.
   */
    val profileRepository : ProfileRepository by lazy {
        ProfileRepositoryFirestore(firestore)
    }
    val userProfileRepository : UserProfileRepository by lazy {
        UserProfileRepositoryFirestore(firestore)
    }
    val authRepository : AuthRepository by lazy {
        AuthRepositoryFirebase(auth) // inject Firebase auth
    }
}