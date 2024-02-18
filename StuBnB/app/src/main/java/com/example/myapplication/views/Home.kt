package com.example.myapplication.views

import androidx.compose.runtime.Composable
import com.example.myapplication.screens.DisplayBottomBar

@Composable
fun Home() {
    DisplayBottomBar(0)
}

@Composable
fun Profile() {
    DisplayBottomBar(4)
}