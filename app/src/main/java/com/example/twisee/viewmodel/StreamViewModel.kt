package com.example.twisee.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twisee.domain.model.Stream
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.twisee.data.remote.TwitchRepository


@HiltViewModel
class StreamViewModel @Inject constructor(
    private val repository: TwitchRepository
) : ViewModel() {

    private val _streams = MutableStateFlow<List<Stream>>(emptyList())
    val streams = _streams.asStateFlow()


    // загрузка стримов
    fun fetchTopStreams() {
        viewModelScope.launch {
            try {
                repository.clearAuthToken() //очистка токена
                val newStreams = repository.fetchTopStreams()
                _streams.value = _streams.value + newStreams
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun hasMorePages(): Boolean = repository.hasMorePages()
}