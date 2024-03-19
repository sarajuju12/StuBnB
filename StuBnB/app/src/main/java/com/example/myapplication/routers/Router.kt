package com.example.myapplication.routers

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.views.*
import com.example.myapplication.views.detailPages.*


@RequiresApi(Build.VERSION_CODES.O)
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
                    //Home()
                    //Profile()
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

                is Screen.ChatBox -> {
                    val primaryUser = (currentState.value as Screen.ChatBox).primaryUser
                    val secondaryUser = (currentState.value as Screen.ChatBox).secondaryUser
                    ChatBoxWrapper(primaryUser, secondaryUser)
                }

                is Screen.UploadHousing -> {
                    UploadHousing()
                }

                // details
                is Screen.House -> {
                    House((currentState.value as Screen.House).housingItem)
                }

                is Screen.Inventory -> {
                    Inventory((currentState.value as Screen.Inventory).inventoryItem)
                }
            }
        }
    }
}