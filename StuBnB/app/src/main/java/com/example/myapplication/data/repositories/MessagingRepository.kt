package com.example.myapplication.data.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myapplication.models.ChatMessage
import com.example.myapplication.models.Inventory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime

class MessagingRepository : IMessagingRepository {
    override fun getMessagesFromDB(userOne: String, userTwo: String): List<ChatMessage>{
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("messages")
        val messageList: MutableList<ChatMessage> = mutableListOf()

        // messages / userFirst++userSecond <- sorted alphabetically
        val keyToAccessChatBetweenTwo = if (userOne < userTwo) {
            "$userOne$userTwo"
        } else {
            "$userTwo$userOne"
        }

        myRef.child(keyToAccessChatBetweenTwo).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(chatSnapshot: DataSnapshot) {
                for (messageSnapshot in chatSnapshot.children) {
                    val message = messageSnapshot.getValue(ChatMessage::class.java)
                    message?.let { messageList.add(it) }
                }

                // callback.onInventoryLoaded(inventoryList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("getMessagesFromDB", "Failed to get messages between user $userOne and $userTwo.", databaseError.toException())
            }
        })

        return messageList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun newMessageToDB(sendingUser: String, receivingUser: String, message: String): Boolean {
        println("\n\n\n\n HELLO, I HAVE --$message-- AS MY MESSAGEE!")
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("messages")

        // messages / userFirst++userSecond <- sorted alphabetically
        val keyToAccessChatBetweenTwo = if (sendingUser < receivingUser) {
            "$sendingUser++$receivingUser"
        } else {
            "$receivingUser++$sendingUser"
        }
        println("\n\n\n\n HELLO, I HAVE --$keyToAccessChatBetweenTwo-- AS MY KEY!")

        val newMessage = ChatMessage(LocalDateTime.now(), sendingUser, receivingUser, message)

        myRef.child(keyToAccessChatBetweenTwo).push().setValue(newMessage)

        //myRef.child(newInventoryItem.userId).child(newInventoryItem.name).setValue(newInventoryItem)
        //    }

        return true;
    }
}