package com.example.bookcraftapplication.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bookcraftapplication.MainActivity
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _signInResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    val signInResult: StateFlow<ResultAuth<Boolean>?> = _signInResult
    private val _signUpResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    val signUpResult: StateFlow<ResultAuth<Boolean>?> = _signUpResult
    private val _signOutResult = MutableStateFlow<ResultAuth<Boolean>?>(null)
    private val _deleteAccountResult = MutableStateFlow<ResultAuth<Boolean>?>(null)

    val signOutResult: StateFlow<ResultAuth<Boolean>?> = _signOutResult
    val deleteAccountResult: StateFlow<ResultAuth<Boolean>?> = _deleteAccountResult

    // Return a StateFlow so that the composable can always update
    // based when the value changes
    fun currentUser(): StateFlow<User?> {
        return authRepository.currentUser()
    }
    fun signUp(email: String, password: String) {
        _signUpResult.value = ResultAuth.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val success = authRepository.signUp(email, password)
                _signUpResult.value = ResultAuth.Success(success)
            } catch (e: FirebaseAuthException) {
                _signUpResult.value = ResultAuth.Failure(e)
            } finally {
                // Reset the others since they are no longer applicable
                _signInResult.value = ResultAuth.Inactive
                _signOutResult.value = ResultAuth.Inactive
                _deleteAccountResult.value = ResultAuth.Inactive
            }
        }
    }
    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signIn(email, password)
        }
    }
    fun signOut() {
        authRepository.signOut()
    }
    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.delete()
        }
    }
}

/* ViewModel Factory that will create our view model by injecting the
      authRepository from the module.
 */
class AuthViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(MainActivity.appModule.authRepository) as T
    }
}