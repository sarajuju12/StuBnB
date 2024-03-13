package com.example.myapplication.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class LikeButtonItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun DisplayHeartButton(modifier: Modifier = Modifier, onHeartButtonClick: () -> Unit) {

    var isSelected by remember { mutableStateOf(false) }

    val button = if (isSelected) {
        LikeButtonItem(Icons.Filled.Favorite, Icons.Outlined.Favorite)
    } else {
        LikeButtonItem(Icons.Outlined.Favorite, Icons.Filled.Favorite)
    }

    // Ensure you use the incoming modifier, with any additional modifications appended
    IconButton(
        onClick = { isSelected = !isSelected
                    onHeartButtonClick()
                  },
        modifier = modifier.size(24.dp) // append the size to the incoming modifier
    ) {
        Icon(
            imageVector = if (isSelected) button.selectedIcon else button.unselectedIcon,
            contentDescription = "Heart",
            tint = if (isSelected) Color.Red else Color.Gray
        )
    }
}



