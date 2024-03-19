package com.example.myapplication.views

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.repositories.MessagingCallback
import com.example.myapplication.data.repositories.MessagingRepository
import com.example.myapplication.models.ChatMessage
import com.example.myapplication.models.Inventory
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.example.myapplication.screens.InventoryList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBoxWrapper(primaryUser: String, secondaryUser: String){
    var newMessage by remember { mutableStateOf(TextFieldValue()) }
    val messageRepository = MessagingRepository()

    // Initialize with SnapshotStateList for observability
    val messageList = remember { mutableStateListOf<ChatMessage>() }
    val listState = rememberLazyListState()

    val database = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.getReference("messages")

    // messages / userFirst++userSecond <- sorted alphabetically
    val keyToAccessChatBetweenTwo = if (primaryUser < secondaryUser) {
        "$primaryUser++$secondaryUser"
    } else {
        "$primaryUser++$secondaryUser"
    }

    myRef.child(keyToAccessChatBetweenTwo).addValueEventListener(object : ValueEventListener {
        override fun onDataChange(chatSnapshot: DataSnapshot) {
            val tempMessageList = mutableListOf<ChatMessage>()
            for (messageSnapshot in chatSnapshot.children) {
                val message = messageSnapshot.getValue(ChatMessage::class.java)
                message?.let {
                    tempMessageList.add(it)
                }
            }
            messageList.clear()
            messageList.addAll(tempMessageList)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e("getMessagesFromDB", "Failed to get messages between user $primaryUser and $secondaryUser.", databaseError.toException())
        }
    })

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = primaryUser + "++" + secondaryUser) },
            navigationIcon = {
                IconButton(onClick = { Navigator.navigate(Screen.HomeInbox) }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        // Display chat messages
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(messageList.size) {
                ChatMessageItem(messageList[it])
            }
        }

        LaunchedEffect(messageList.size) {
            if (messageList.isNotEmpty()) {
                listState.animateScrollToItem(index = messageList.size - 1)
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
                    messageRepository.newMessageToDB(primaryUser, secondaryUser, newMessage.text)
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