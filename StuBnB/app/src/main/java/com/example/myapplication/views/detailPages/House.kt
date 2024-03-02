package com.example.myapplication.views.detailPages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.components.ActionButton
import com.example.myapplication.components.BackButton
import com.example.myapplication.screens.DisplayBottomBar
import com.example.myapplication.routers.*
import com.example.myapplication.data.HomeViewModel
import com.example.myapplication.models.Housing


@Composable
fun House(HousingItem: Housing) {
    Box(modifier = Modifier.fillMaxSize()) {
        BackButton(
            buttonClicked = {
                Navigator.navigate(Screen.HomeHousing)
            },
            modifier = Modifier
                .size(78.dp)
                .align(Alignment.TopStart)
        )
    }

    Text(
        text = HousingItem.name,
    )
}