package com.example.myapplication.data.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myapplication.data.repositories.MessagingCallback
import com.example.myapplication.data.repositories.messaging.IMessagingRepository
import com.example.myapplication.models.ChatMessage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime

class MessagingRepository : IMessagingRepository {
    override fun getMessagesFromDB(userOne: String, userTwo: String, callback: MessagingCallback){
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("messages")
        val messageList: MutableList<ChatMessage> = mutableListOf()

        // messages / userFirst++userSecond <- sorted alphabetically
        val keyToAccessChatBetweenTwo = if (userOne < userTwo) {
            "$userOne++$userTwo"
        } else {
            "$userTwo++$userOne"
        }

        println("------------------------")
        println("key is: $keyToAccessChatBetweenTwo");
        println("------------------------")

        myRef.child(keyToAccessChatBetweenTwo).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(chatSnapshot: DataSnapshot) {
                println("hey im here doe")
                for (messageSnapshot in chatSnapshot.children) {
                    val message = messageSnapshot.getValue(ChatMessage::class.java)
                    println("hey im here doe 2")
                    message?.let {
                        messageList.add(it)
                        println("added ${it.message}");
                    }

                }

                callback.onMessagesLoaded(messageList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("getMessagesFromDB", "Failed to get messages between user $userOne and $userTwo.", databaseError.toException())
            }
        })
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

        val currentTime = LocalDateTime.now();
        val currentYear = currentTime.year;
        val currentDay = currentTime.dayOfYear;
        val currentHour = currentTime.hour;
        val currentMinute = currentTime.minute;
        val currentSecond = currentTime.second;

        val newMessage = ChatMessage(currentYear, currentDay, currentHour, currentMinute, currentSecond, sendingUser, receivingUser, message)

        myRef.child(keyToAccessChatBetweenTwo).push().setValue(newMessage)

        //myRef.child(newInventoryItem.userId).child(newInventoryItem.name).setValue(newInventoryItem)
        //    }

        return true;
    }
}