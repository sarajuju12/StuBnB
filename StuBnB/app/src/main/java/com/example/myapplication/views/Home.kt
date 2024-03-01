package com.example.myapplication.views

import androidx.compose.runtime.Composable
import com.example.myapplication.screens.DisplayBottomBar

@Composable
fun Home(index: Int = 0) {
    DisplayBottomBar(index)
}

@Composable
fun Profile() {
    DisplayBottomBar(4)
}