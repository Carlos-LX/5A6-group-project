package com.example.bookcraftapplication.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookcraftapplication.profile.ProfileData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(private val userProfileRepository: UserProfileRepository) : ViewModel() {
    // Private MutableStateFlow to track the list of ProfileData
    private val _allProfiles = MutableStateFlow(listOf<ProfileData>())

    // Public StateFlow for external observation
    val allProfiles: StateFlow<List<ProfileData>> = _allProfiles.asStateFlow()

    init {
        // In the init block, launch a coroutine to collect the flow from the repository
        viewModelScope.launch {
            userProfileRepository.getProfiles().collect { allProfiles ->
                // Update the MutableStateFlow with the latest data
                _allProfiles.value = allProfiles
            }
        }
    }
}
