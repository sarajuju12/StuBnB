package com.example.myapplication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.components.ActionButton
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.example.myapplication.screens.InventoryItem

@Composable @Preview
fun Inbox() {
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
        items(15) { index ->
            OpenChat("Raegan Wong", "Mad Mad Mad TREASURE HUNT!")
        }
    }
}

@Composable
fun OpenChat(name: String, latestMessage: String) {
    var outputName = if(name.length > 24) {name.substring(0, 23)} else {name};
    var outputLatestMessage = if(latestMessage.length > 43) {latestMessage.substring(0, 42) + "..."} else {latestMessage};

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Image
        Image(
            painter = painterResource (id = R.drawable.picture), // Placeholder image
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


