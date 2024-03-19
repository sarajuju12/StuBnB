package com.example.myapplication.data.repositories

import com.example.myapplication.models.ChatMessage
import com.example.myapplication.models.Inventory

interface MessagingCallback{
    fun onMessagesLoaded(messageList: MutableList<ChatMessage>)
}