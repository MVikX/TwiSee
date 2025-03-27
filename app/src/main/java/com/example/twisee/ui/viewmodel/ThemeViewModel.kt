package com.example.twisee.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twisee.data.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val userPrefs: UserPreferences
):ViewModel(){

    val isDarkTheme = userPrefs.isDarkTheme
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)


    fun toggleTheme() {
        viewModelScope.launch {
            userPrefs.setDarkTheme(!isDarkTheme.value)
        }
    }
}