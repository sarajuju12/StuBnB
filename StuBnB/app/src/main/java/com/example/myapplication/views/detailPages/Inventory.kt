package com.example.myapplication.views.detailPages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.components.ActionButton
import com.example.myapplication.screens.DisplayBottomBar
import com.example.myapplication.routers.*
import com.example.myapplication.data.HomeViewModel


// when clicking on a row and from a housing tab
@Composable
fun Inventory() {
    Column(modifier = Modifier.fillMaxSize()) {
        ActionButton(value = "back",
            buttonClicked = { Navigator.navigate(Screen.Home(1)) },

            isEnabled = true)
    }
}