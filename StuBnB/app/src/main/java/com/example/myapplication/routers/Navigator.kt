package com.example.myapplication.routers

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object CreateAccount: Screen()
    object Login: Screen()
}

object Navigator {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.Login)

    fun navigate(dest: Screen) {
        currentScreen.value = dest
    }
}