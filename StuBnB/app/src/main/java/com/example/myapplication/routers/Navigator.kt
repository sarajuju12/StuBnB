package com.example.myapplication.routers

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.sp

sealed class Screen {
    object CreateAccount: Screen()
    object Login: Screen()
    object Home: Screen()

    object House: Screen()
}

object Navigator {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.Login)

    fun navigate(dest: Screen) {
        currentScreen.value = dest
    }
}