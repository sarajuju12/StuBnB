package com.example.myapplication.data.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myapplication.data.repositories.messaging.IMessagingRepository
import com.example.myapplication.models.ChatMessage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime

class MessagingRepository : IMessagingRepository {
    override fun getAllMessagesInvolvingUserLatest(userEmail: String, callback: (MutableList<ChatMessage>) -> Unit){
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("messages")
        val latestMessageList: MutableList<ChatMessage> = mutableListOf()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(allOpenChatsSnapshot: DataSnapshot) {
                for (openChat in allOpenChatsSnapshot.children) {
                    // includes my userEmail, then it's one of my open chats.
                    if(openChat.key?.contains(userEmail) ?: false){
                        // get the latest message and put it in.
                        val latestMessage = openChat.child("latest").getValue(ChatMessage::class.java)
                        latestMessage?.let {
                            latestMessageList.add(it)
                        }
                    }else{
                        continue
                    }
                }
                callback(latestMessageList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("getAllMessagesInvolvingUserLatest", "Failed to get all messages involving $userEmail.", databaseError.toException())
            }
        })
    }
    override fun getMessagesFromDB(userOne: String, userTwo: String, callback: (MutableList<ChatMessage>) -> Unit){
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("messages")
        val messageList: MutableList<ChatMessage> = mutableListOf()

        // by convention, we make it so that the key for where a message is stored is the two emails, in order alphabetically.
        val keyToAccessChatBetweenTwo = if (userOne < userTwo) {
            "$userOne++$userTwo"
        } else {
            "$userTwo++$userOne"
        }

        myRef.child(keyToAccessChatBetweenTwo).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(chatSnapshot: DataSnapshot) {
                for (messageSnapshot in chatSnapshot.children) {
                    val message = messageSnapshot.getValue(ChatMessage::class.java)
                    message?.let {
                        messageList.add(it)
                    }
                }
                callback(messageList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("getMessagesFromDB", "Failed to get messages between user $userOne and $userTwo.", databaseError.toException())
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun newMessageToDB(sendingUser: String, receivingUser: String, message: String): Boolean {
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("messages")

        // by convention, we make it so that the key for where a message is stored is the two emails, in order alphabetically.
        val keyToAccessChatBetweenTwo = if (sendingUser < receivingUser) {
            "$sendingUser++$receivingUser"
        } else {
            "$receivingUser++$sendingUser"
        }

        val currentTime = LocalDateTime.now();
        val currentYear = currentTime.year;
        val currentDay = currentTime.dayOfYear;
        val currentHour = currentTime.hour;
        val currentMinute = currentTime.minute;
        val currentSecond = currentTime.second;

        val newMessage = ChatMessage(currentYear, currentDay, currentHour, currentMinute, currentSecond, sendingUser, receivingUser, message)
        myRef.child(keyToAccessChatBetweenTwo).push().setValue(newMessage)
        myRef.child(keyToAccessChatBetweenTwo).child("latest").setValue(newMessage)

        return true;
    }
}