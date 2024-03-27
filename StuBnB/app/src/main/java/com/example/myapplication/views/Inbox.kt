package com.example.myapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.myapplication.R
import com.example.myapplication.data.LoginViewModel
import com.example.myapplication.data.repositories.MessagingRepository
import com.example.myapplication.models.ChatMessage
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.example.myapplication.screens.getNameOfUser
import com.example.myapplication.screens.getProfilePic

@Composable @Preview
fun Inbox(loginViewModel: LoginViewModel = viewModel()) {
    val messageRepository = MessagingRepository()
    val openChatsState = remember { mutableStateListOf<ChatMessage>() }

    messageRepository.getAllMessagesInvolvingUserLatest(loginViewModel.getEncryptedEmail()) { openChats ->
        openChatsState.clear()
        openChatsState.addAll(openChats)
    }

    LazyColumn {
        item {
            Text(
                text = "Chats",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            )
        }
        items(openChatsState.size) {
            val secondaryUserEmail = if(openChatsState[it].senderUserID == loginViewModel.getEncryptedEmail()){openChatsState[it].receiverUserID} else {openChatsState[it].senderUserID}
            OpenChat(secondaryUserEmail, openChatsState[it].message)
        }
    }


}

@Composable
fun OpenChat(secondPersonEmail: String, latestMessage: String, loginViewModel: LoginViewModel = viewModel()) {
    val userNameState = remember { mutableStateOf("") }
    val userPicState = remember { mutableStateOf("") }

    getNameOfUser({ userName ->
        userNameState.value = userName ?: "User not found"
    }, secondPersonEmail)

    getProfilePic({ profilePicUrl ->
        if (profilePicUrl != null) {
            userPicState.value = profilePicUrl
        }
    }, secondPersonEmail)

    val painter = rememberImagePainter(
        data = userPicState.value, // URL of the user's profile picture
        builder = {
            error(R.drawable.profile) // Default profile picture drawable
            transformations(CircleCropTransformation()) // Apply circular transformation
        }
    )

    var outputName = if(userNameState.value.length > 24) {userNameState.value.substring(0, 23)} else {userNameState.value};
    var outputLatestMessage = if(latestMessage.length > 43) {latestMessage.substring(0, 42) + "..."} else {latestMessage};

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                // Navigate to the chat screen
                Navigator.navigate(Screen.ChatBox(loginViewModel.getEncryptedEmail(), secondPersonEmail)) // Assuming "chat/${name}" is your destination
            }
    ) {
        // Image
        Image(
            painter = painter,
            contentDescription = "Inventory Picture",
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Inventory Details
        Column(
            modifier = Modifier
                .weight(1f), // Takes remaining space
            verticalArrangement = Arrangement.Center // Center vertically
        ) {
            Text(text = outputName, fontSize = 20.sp, fontWeight = FontWeight.Medium)
            Text(text = outputLatestMessage, fontSize = 14.sp)
        }
    }
}


