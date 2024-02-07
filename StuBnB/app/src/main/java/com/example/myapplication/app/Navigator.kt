package com.example.myapplication.app

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object CreateAccount: Screen()
}

object Navigator {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.CreateAccount)

    fun navigate(dest: Screen) {
        currentScreen.value = dest
    }
}