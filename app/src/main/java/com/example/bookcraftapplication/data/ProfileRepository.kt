package com.example.bookcraftapplication.data

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun saveProfile(profileData: ProfileData)
    fun getProfile(): Flow<ProfileData>
    suspend fun clear()
}