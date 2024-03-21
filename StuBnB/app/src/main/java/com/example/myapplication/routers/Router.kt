package com.example.myapplication.routers

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.screens.getNameOfUser
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
                    val primaryUser = (currentState.value as Screen.ChatBox).primaryUserEmail
                    val secondaryUser = (currentState.value as Screen.ChatBox).secondaryUserEmail

                    // create primary user userID instead of email
                    val userNamePrimaryState = remember { mutableStateOf("") }
                    getNameOfUser({ userName ->
                        userNamePrimaryState.value = userName ?: "User not found"
                    }, secondaryUser)

                    // create secondary user userID instead of email
                    val userNameSecondaryState = remember { mutableStateOf("") }
                    getNameOfUser({ userName ->
                        userNameSecondaryState.value = userName ?: "User not found"
                    }, secondaryUser)

                    ChatBoxWrapper(primaryUser, secondaryUser, userNamePrimaryState.value, userNameSecondaryState.value)
                }

                is Screen.UploadHousing -> {
                    UploadHousing()
                }

                // details
                is Screen.House -> {
                    House((currentState.value as Screen.House).housingItem, (currentState.value as Screen.House).fromHos)
                }

                is Screen.Inventory -> {
                    Inventory((currentState.value as Screen.Inventory).inventoryItem, (currentState.value as Screen.Inventory).fromInv)
                }
            }
        }
    }
}