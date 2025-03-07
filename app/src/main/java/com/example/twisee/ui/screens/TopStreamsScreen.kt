package com.example.twisee.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.twisee.domain.model.Stream
import com.example.twisee.viewmodel.StreamViewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale


@Composable
fun TopStreamsScreen(viewModel: StreamViewModel = hiltViewModel()) {
    val streams = viewModel.streams.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.fetchTopStreams()
    }

    if (streams.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Нет стримов", textAlign = TextAlign.Center)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            itemsIndexed(streams) { index, stream ->
                StreamItem(stream)

                if (index == streams.size - 1 && viewModel.hasMorePages()) {
                    LaunchedEffect(Unit) {
                        viewModel.fetchTopStreams()
                    }
                }
            }
        }
    }
}

@Composable
fun StreamItem(stream: Stream) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(
                    "https://www.twitch.tv/${stream.userName}")
                )
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(stream.thumbnailUrl),
                contentDescription = "Stream Thumbnail",
                modifier = Modifier.fillMaxWidth().height(180.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = stream.userName, style = MaterialTheme.typography.titleMedium)
                Text(text = stream.title, style = MaterialTheme.typography.bodyMedium)
                Text(text = "Онлайн: ${stream.viewerCount}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
