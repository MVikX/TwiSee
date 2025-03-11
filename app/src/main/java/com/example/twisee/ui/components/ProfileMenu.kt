package com.example.twisee.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfileMenu(
    username: String,
    avatarUrl: String?,
    onLogout: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingClick: () -> Unit,
    textColor: androidx.compose.ui.graphics.Color
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize()) {
        TextButton(onClick = { expanded = true }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (avatarUrl != null) {
                    Image(
                        painter = rememberAsyncImagePainter(avatarUrl),
                        contentDescription = "Аватар",
                        modifier = Modifier.size(32.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Профиль",
                        tint = textColor,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(username, color = textColor)
            }
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Профиль") },
                onClick = {
                    expanded = false
                    onProfileClick()
                }
            )
            DropdownMenuItem(
                text = { Text("Настройки") },
                onClick = {
                    expanded = false
                    onSettingClick()
                }
            )
            DropdownMenuItem(
                text = { Text("Выход") },
                onClick = {
                    expanded = false
                    onLogout()
                }
            )
        }
    }
}