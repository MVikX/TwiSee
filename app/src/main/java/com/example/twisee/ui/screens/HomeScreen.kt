package com.example.twisee.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun HomeScreen(username: String?, avatarUrl: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = rememberAsyncImagePainter(avatarUrl),
                contentDescription = "Аватар",
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            username?.let {
                Text(
                    text = it,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}