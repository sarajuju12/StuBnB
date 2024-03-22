package com.example.myapplication.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

data class ChatMessage(
    val timeYear: Int,
    val timeDay: Int,
    val timeHour: Int,
    val timeMinute: Int,
    val timeSecond: Int,
    val senderUserID: String,
    val receiverUserID: String,
    val message: String
){
    @RequiresApi(Build.VERSION_CODES.O)
    constructor() : this(0, 0,0 ,0,0, "user1", "user2", "hello")
}