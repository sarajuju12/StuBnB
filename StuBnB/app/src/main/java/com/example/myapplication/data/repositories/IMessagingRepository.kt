package com.example.myapplication.data.repositories.messaging

import com.example.myapplication.data.repositories.MessagingCallback
import com.example.myapplication.models.ChatMessage

interface IMessagingRepository{
    fun getMessagesFromDB(userOne: String, userTwo: String, callback: MessagingCallback)
    fun newMessageToDB(sendingUser: String, receivingUser: String, message: String): Boolean
}

/*

Messages
    // sort alphabetically by userID.
    user_bob++user_anne
        time_sent: "message" by: userID
 */
