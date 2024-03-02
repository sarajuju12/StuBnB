package com.example.myapplication.routers

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object CreateAccount: Screen()
    object Login: Screen()
    // Home tabs
    object HomeHousing : Screen()
    object HomeInventory : Screen()
    object HomeWishlist : Screen()
    object HomeInbox : Screen()
    object HomeProfile : Screen()
    object UploadInventory: Screen()
    object UploadHousing: Screen()
    //detail pages
    object House: Screen()
    object Inventory: Screen()
}

object Navigator {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.Login)

    fun navigate(dest: Screen) {
        currentScreen.value = dest
    }
}