package com.example.bookcraftapplication.profile

import com.example.bookcraft.data.Book
import com.example.bookcraftapplication.profile.ProfileData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface ProfileRepository {
    suspend fun saveProfile(userProfile: ProfileData)
    suspend fun getProfile(): Flow<ProfileData> = callbackFlow {}

    suspend fun clear()

    }