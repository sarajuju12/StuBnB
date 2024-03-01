package com.example.myapplication.routers

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.views.*
import com.example.myapplication.views.detailPages.*


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
                    // Profile()
                }

                is Screen.HomeHousing -> {
                    Home(0)
                }

                is Screen.HomeInventory -> {
                    Home(1)
                }

                is Screen.HomeWishlist -> {
                    Home(2)
                }

                is Screen.HomeInbox -> {
                    Home(3)
                }

                is Screen.HomeProfile -> {
                    Home(4)
                }

                is Screen.UploadInventory -> {
                    UploadInventory()
                }

                is Screen.UploadHousing -> {
                    UploadHousing()
                }
                // details
                is Screen.House -> {
                    House()
                }
                is Screen.Inventory -> {
                    Inventory()
                }
            }
        }
    }
}