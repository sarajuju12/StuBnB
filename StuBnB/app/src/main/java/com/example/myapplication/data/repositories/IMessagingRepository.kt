package com.example.myapplication.data.repositories

import com.example.myapplication.models.ChatMessage

interface IMessagingRepository{
    fun getAllMessagesInvolvingUserLatest(userEmail: String, callback: (MutableList<ChatMessage>) -> Unit)
    fun getMessagesFromDB(userOne: String, userTwo: String, callback: (MutableList<ChatMessage>) -> Unit)
    fun newMessageToDB(sendingUser: String, receivingUser: String, message: String): Boolean
}
