package com.example.twisee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.example.twisee.viewmodel.StreamViewModel
import androidx.compose.foundation.lazy.rememberLazyListState
import com.example.twisee.ui.components.StreamItem

@Composable
fun TopStreamsScreen(
    streamViewModel: StreamViewModel,
) {
    val streams by streamViewModel.streams.collectAsState()
    val isLoadingFirstPage by streamViewModel.isLoadingFirstPage.collectAsState()
    val isLoadingMore by streamViewModel.isLoadingMore.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        streamViewModel.fetchTopStreams()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoadingFirstPage -> CircularProgressIndicator()
            streams.isEmpty() -> Text("Нет стримов")
            else -> LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(streams) { index, stream ->
                    StreamItem(stream)

                    if (index == streams.size - 3 && !isLoadingMore) {
                        streamViewModel.loadMoreStreams()
                    }
                }

                if (isLoadingMore) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}