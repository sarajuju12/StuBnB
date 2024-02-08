package com.example.myapplication.routers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.screens.CreateAccount

@Composable
fun Entry() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        CreateAccount()
    }
}