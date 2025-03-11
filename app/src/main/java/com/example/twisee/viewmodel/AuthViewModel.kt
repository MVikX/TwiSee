package com.example.twisee.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twisee.auth.AuthHandler
import com.example.twisee.data.models.UserInfo
import com.example.twisee.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _authToken = MutableStateFlow<String?>(null)
    val authToken: StateFlow<String?> = _authToken.asStateFlow()

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo.asStateFlow()

    private val _isUserLoading = MutableStateFlow(false)

    init {
        loadToken()
    }

    fun getTwitchAuthUrl(): String = authUseCase.getTwitchAuthUrl()

    private fun loadToken() {
        viewModelScope.launch {
            val storedToken = authUseCase.getAuthToken()
            _authToken.value = storedToken

            if (storedToken != null) {
                fetchUserInfo()
            }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            authUseCase.saveAuthToken(token)
            _authToken.value = token
            fetchUserInfo()
        }
    }

    private fun fetchUserInfo() {
        viewModelScope.launch {
            _isUserLoading.value = true
            try {
                val user = authUseCase.fetchUserInfo()
                _userInfo.value = user
            } catch (_: Exception) {
                _userInfo.value = null
            } finally {
                _isUserLoading.value = false
            }
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            authUseCase.clearAuthToken()
            _authToken.value = null
            _userInfo.value = null
        }
    }

    fun handleIntent(intent: Intent?) {
        val token = AuthHandler.extractToken(intent)
        if (token != null) {
            saveToken(token)
        }
    }
}