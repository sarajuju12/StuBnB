package com.example.myapplication.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

data class ChatMessage(
    val timeCreated: LocalDateTime,
    val senderUserID: String,
    val receiverUserID: String,
    val message: String
){
    @RequiresApi(Build.VERSION_CODES.O)
    constructor() : this(LocalDateTime.now(), "user1", "user2", "hello")
}

