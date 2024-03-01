package com.example.myapplication.routers

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object CreateAccount: Screen()
    object Login: Screen()
    // Home tabs
    class HomeHousing : Screen()
    class HomeInventory : Screen()
    class HomeWishlist : Screen()
    class HomeInbox : Screen()
    class HomeProfile : Screen()
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