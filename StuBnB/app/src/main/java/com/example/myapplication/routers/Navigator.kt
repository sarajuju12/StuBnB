package com.example.myapplication.routers

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object CreateAccount: Screen()
    object Login: Screen()
    class Home(val selectedIndex: Int = 0) : Screen()
    object UploadInventory: Screen()
    object Profile: Screen()
    object UploadHousing: Screen()
    //detail pages
    object House: Screen()
    object Inventory: Screen()
}

object Navigator {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.Login)

    fun navigate(dest: Screen, index: Int = 0) {
        currentScreen.value = dest

        if (index != 0){
            currentScreen.value = Screen.Home(index)
        }
    }
}