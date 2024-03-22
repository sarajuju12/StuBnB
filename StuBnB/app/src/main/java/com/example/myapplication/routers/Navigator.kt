package com.example.myapplication.routers

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.models.*

const val Inv: Int = 1
const val Hos: Int = 0
const val Wish: Int = 2
const val Prof: Int = 4
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
    data class ChatBox(val primaryUserEmail: String, val secondaryUserEmail: String) : Screen()
    //detail pages
    data class House(val housingItem: Housing, val fromWhere: Int) : Screen()
    data class Inventory(val inventoryItem: com.example.myapplication.models.Inventory, val fromWhere: Int) : Screen()
}

object Navigator {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.Login)

    fun navigate(dest: Screen) {
        currentScreen.value = dest
    }
}
