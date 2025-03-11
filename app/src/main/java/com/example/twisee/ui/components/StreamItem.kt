package com.example.twisee.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.twisee.domain.model.Stream

@Composable
fun StreamItem(stream: Stream) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // stream thumbnail
            Image(
                painter = rememberAsyncImagePainter(stream.thumbnailUrl),
                contentDescription = "Превью стрима",
                modifier = Modifier.fillMaxWidth().height(180.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            // stream details
            Text(text = stream.userName, style = MaterialTheme.typography.titleMedium)
            Text(text = stream.title, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Зрителей: ${stream.viewerCount}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}