package com.example.myapplication.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.repositories.MessagingRepository
import com.example.myapplication.models.ChatMessage
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBox(primaryUser: String, secondaryUser: String) {
    var newMessage by remember { mutableStateOf(TextFieldValue()) }
    val mesRep = MessagingRepository()
    //val chatMessages = mesRep.getMessagesFromDB(primaryUser, secondaryUser)

    val chatMessages = remember {
        listOf(
            ChatMessage(LocalDateTime.now(), "user1", "user2", "Hello"),
            ChatMessage(LocalDateTime.now(), "user2", "user1", "Hi there!"),
            ChatMessage(LocalDateTime.now(), "user1", "user2", "How are you?")
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Display chat messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(chatMessages.size) {
                ChatMessageItem(chatMessages[it])
            }
        }

        // Input box for typing new message
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                modifier = Modifier.weight(1f),
                label = { Text("Type your message") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    mesRep.newMessageToDB(primaryUser, secondaryUser, newMessage.text)
                    newMessage = TextFieldValue()
                }
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun ChatMessageItem(chatMessage: ChatMessage) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Handle click on message if needed */ },
        color = Color.LightGray,
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = chatMessage.senderUserID)
            Text(text = chatMessage.message)
        }
    }
}

// Sample usage
@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun SampleChatScreen() {
    /*val chatMessages = remember {
        listOf(
            ChatMessage(LocalDateTime.now(), "user1", "user2", "Hello"),
            ChatMessage(LocalDateTime.now(), "user2", "user1", "Hi there!"),
            ChatMessage(LocalDateTime.now(), "user1", "user2", "How are you?")
        )
    }*/

    ChatBox("user1", "user2")
}