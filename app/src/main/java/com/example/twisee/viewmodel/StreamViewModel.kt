package com.example.twisee.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twisee.domain.model.Stream
import com.example.twisee.data.remote.TwitchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StreamViewModel @Inject constructor(
    private val repository: TwitchRepository
) : ViewModel() {

    private val _streams = MutableStateFlow<List<Stream>>(emptyList())
    val streams: StateFlow<List<Stream>> = _streams

    private val _isLoadingFirstPage = MutableStateFlow(false)
    val isLoadingFirstPage: StateFlow<Boolean> = _isLoadingFirstPage

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore

    private var paginationCursor: String? = null

    fun fetchTopStreams(reset: Boolean = false) {
        if (_isLoadingFirstPage.value) return
        viewModelScope.launch {
            _isLoadingFirstPage.value = true
            try {
                val response = repository.fetchTopStreams(if (reset) null else paginationCursor)
                paginationCursor = response.second
                _streams.value = if (reset) response.first else _streams.value + response.first
            } catch (_: Exception) {
            } finally {
                _isLoadingFirstPage.value = false
            }
        }
    }

    fun loadMoreStreams() {
        if (_isLoadingMore.value || paginationCursor == null) return
        viewModelScope.launch {
            _isLoadingMore.value = true
            try {
                val response = repository.fetchTopStreams(paginationCursor)
                paginationCursor = response.second
                _streams.value += response.first
            } catch (_: Exception) {
            } finally {
                _isLoadingMore.value = false
            }
        }
    }

    fun reloadStreams() {
        if (_isLoadingFirstPage.value) return
        fetchTopStreams(reset = true)
    }

    fun fetchTopStreamsAsGuest() {
        viewModelScope.launch {
            if (_isLoadingFirstPage.value) return@launch
            _isLoadingFirstPage.value = true
            try {
                val newStreams = repository.fetchGuestStreams()
                _streams.value = newStreams
            } catch (_: Exception) {
            } finally {
                _isLoadingFirstPage.value = false
            }
        }
    }
}