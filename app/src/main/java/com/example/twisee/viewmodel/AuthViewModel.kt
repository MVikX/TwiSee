package com.example.twisee.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twisee.data.remote.TwitchAuthApi
import com.example.twisee.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authApi: TwitchAuthApi,
    private val authUseCase: AuthUseCase
) : ViewModel() {

    //получение URL
    fun getTwitchAuthUrl(): String {
        return authUseCase.getTwichAutUrl()
    }

    //сохранение токена
    fun saveToken(token: String) {
        viewModelScope.launch {
            println("сохранение токена $token")
            authUseCase.saveAuthToken(token)
        }
    }
}