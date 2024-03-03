package com.example.myapplication.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ChatBox(primaryUser: String, secondaryUser: String) {
    Text("Hello There im Chatbox, with " + primaryUser + " and " + secondaryUser)
}