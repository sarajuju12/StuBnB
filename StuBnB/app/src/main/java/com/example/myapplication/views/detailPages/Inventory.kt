package com.example.myapplication.views.detailPages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.components.BackButton
import com.example.myapplication.routers.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp



@Composable
fun Inventory() {
    Box(modifier = Modifier.fillMaxSize()) {
        BackButton(
            buttonClicked = {
                Navigator.navigate(Screen.HomeInventory)
            },
            modifier = Modifier
                .size(78.dp)
                .align(Alignment.TopStart)
        )
    }
}
