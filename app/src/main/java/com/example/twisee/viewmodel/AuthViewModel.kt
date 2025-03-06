package com.example.twisee.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twisee.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    //получение URL
    fun getTwhichAuthUrl(): String{
        return authUseCase.getTwichAutUrl()
    }

    //сохранение токена
    fun saveToken(token: String) {
        viewModelScope.launch {
            authUseCase.saveAuthToken(token)
        }
    }
}