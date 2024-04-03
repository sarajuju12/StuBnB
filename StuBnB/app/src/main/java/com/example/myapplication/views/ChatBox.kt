package com.example.myapplication.views

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.myapplication.R
import com.example.myapplication.data.repositories.MessagingRepository
import com.example.myapplication.models.ChatMessage
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.example.myapplication.screens.getNameOfUser
import com.example.myapplication.screens.getProfilePic
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBoxWrapper(primaryUserEmail: String, secondaryUserEmail: String, primaryUserName: String, secondaryUserName: String){
    // Get the Chat between two the Two Users
    val database = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.getReference("messages")
    val messageList = remember { mutableStateListOf<ChatMessage>() }

    // by convention, we make it so that the key for where a message is stored is the two emails, in order alphabetically.
    val keyToAccessChatBetweenTwo = if (primaryUserEmail < secondaryUserEmail) {
        "$primaryUserEmail++$secondaryUserEmail"
    } else {
        "$secondaryUserEmail++$primaryUserEmail"
    }

    myRef.child(keyToAccessChatBetweenTwo).addValueEventListener(object : ValueEventListener {
        override fun onDataChange(chatSnapshot: DataSnapshot) {
            val tempMessageList = mutableListOf<ChatMessage>()
            for (messageSnapshot in chatSnapshot.children) {
                if(messageSnapshot.key == "latest") continue
                val message = messageSnapshot.getValue(ChatMessage::class.java)
                message?.let {
                    tempMessageList.add(it)
                }
            }
            messageList.clear()
            messageList.addAll(tempMessageList)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e("getMessagesFromDB", "Failed to get messages between user $primaryUserEmail and $secondaryUserEmail.", databaseError.toException())
        }
    })

    // For uploading/sending a message.
    var newMessage by remember { mutableStateOf(TextFieldValue()) }
    val messageRepository = MessagingRepository()

    // To keep track of where we are in the lazy column. Auto-scrolling down on sending a new message.
    val listState = rememberLazyListState()

    // Profile Picture
    val userPicState = remember { mutableStateOf("") }

    getProfilePic({ profilePicUrl ->
        if (profilePicUrl != null) {
            userPicState.value = profilePicUrl
        }
    }, secondaryUserEmail)

    val painter = rememberImagePainter(
        data = userPicState.value, // URL of the user's profile picture
        builder = {
            error(R.drawable.profile) // Default profile picture drawable
            transformations(CircleCropTransformation()) // Apply circular transformation
        }
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ){
                    IconButton(
                        onClick = { Navigator.navigate(Screen.HomeInbox) },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Spacer(modifier = Modifier.width(0.dp))
                        Image(
                            painter = painter,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(41.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = secondaryUserName,
                            style = TextStyle(fontWeight = FontWeight.W700, fontSize = 19.sp),
                            modifier = Modifier.weight(1f).padding(end = 12.dp)
                        )
                    }
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
                    if(newMessage.text.length > 0){
                        messageRepository.newMessageToDB(primaryUserEmail, secondaryUserEmail, newMessage.text)
                        newMessage = TextFieldValue()
                    }
                }
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun ChatMessageItem(chatMessage: ChatMessage) {
    val senderNameState = remember { mutableStateOf("") }
    getNameOfUser({ userName ->
        senderNameState.value = userName ?: "User not found"
    }, chatMessage.senderUserID)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = Color(0xFFe6e1f9),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = senderNameState.value,
                color = Color.DarkGray,
                style = TextStyle(fontWeight = FontWeight.W700)
            )
            Text(
                text = chatMessage.message,
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.W400)
            )
        }
    }
}