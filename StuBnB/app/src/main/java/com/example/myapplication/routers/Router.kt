package com.example.myapplication.routers

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.views.CreateAccount
import com.example.myapplication.views.Home
import com.example.myapplication.views.Login

@Composable
fun Router() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(targetState = Navigator.currentScreen) { currentState ->
            when (currentState.value) {
                is Screen.CreateAccount -> {
                    CreateAccount()
                }
                is Screen.Login -> {
                    Login()
                }
                is Screen.Home -> {
                    Home()
                }
            }
        }

    }
}