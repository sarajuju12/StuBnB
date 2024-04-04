package com.example.myapplication.views.detailPages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.myapplication.components.BackButton
import com.example.myapplication.components.DisplayHeartButton
import com.example.myapplication.data.LoginViewModel
import com.example.myapplication.data.repositories.MessagingRepository
import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import com.example.myapplication.routers.Inv
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Prof
import com.example.myapplication.routers.Screen
import com.example.myapplication.ui.theme.Purple40
import com.example.myapplication.ui.theme.poppins

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Inventory(inventoryItem: Inventory, fromInv: Int, loginViewModel : LoginViewModel = viewModel()) {
    val pagerState = remember { PagerState() }
    val pageCount = inventoryItem.imageLinks.size

    val msgRepo = MessagingRepository()

    Column {
        HorizontalPager(
            state = pagerState,
            pageCount = pageCount,
            modifier = Modifier.fillMaxWidth().height(200.dp)
        ) { page ->
            val imageUrl = inventoryItem.imageLinks[page]
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Item image",
                modifier = Modifier
                    .fillMaxWidth().aspectRatio(1.5f),
                contentScale = ContentScale.Crop
            )
        }
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pageCount) { iteration ->
                        val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(16.dp)
                        )
                    }
                }
            }

            item {
                Text(text = inventoryItem.name, fontFamily = poppins, fontWeight = FontWeight.SemiBold, style = TextStyle(fontSize = 24.sp))
                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color.Black)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Description: ${inventoryItem.description}", fontFamily = poppins, fontWeight = FontWeight.Normal, style = TextStyle(fontSize = 16.sp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Category: ${inventoryItem.category}", fontFamily = poppins, fontWeight = FontWeight.Normal, style = TextStyle(fontSize = 16.sp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Subject: ${inventoryItem.subject}", fontFamily = poppins, fontWeight = FontWeight.Normal, style = TextStyle(fontSize = 16.sp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Condition: ${inventoryItem.condition}", fontFamily = poppins, fontWeight = FontWeight.Normal, style = TextStyle(fontSize = 16.sp))
                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color.Black)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "$%.2f".format(inventoryItem.price), fontFamily = poppins, fontWeight = FontWeight.SemiBold, style = TextStyle(fontSize = 24.sp))
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Send seller a message",
                        modifier = Modifier.padding(start = 5.dp),
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(5.dp)
                    ) {
                        TextField(
                            value = "Hi, is this still available?",
                            onValueChange = { },
                            enabled = false,
                            label = { },
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(fontFamily = poppins, fontWeight = FontWeight.Normal)
                        )
                        Button(
                            onClick = {
                                msgRepo.newMessageToDB(loginViewModel.getEncryptedEmail(), inventoryItem.userId, "Hi, is your ${inventoryItem.name} still avaliable?")
                                Navigator.navigate(Screen.ChatBox(loginViewModel.getEncryptedEmail(), inventoryItem.userId))
                            },
                            enabled = true,
                            colors = ButtonDefaults.buttonColors(Purple40),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("SEND")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
    // put the back button on the top most layer
    Box(modifier = Modifier.fillMaxSize()) {
        BackButton(
            buttonClicked = {
                if (fromInv == Inv) Navigator.navigate(Screen.HomeInventory)
                else if (fromInv == Prof) Navigator.navigate(Screen.HomeProfile)
                else Navigator.navigate(Screen.HomeWishlist)
            },
            modifier = Modifier
                .size(78.dp)
                .align(Alignment.TopStart)
        )

        DisplayHeartButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 16.dp)
                .size(36.dp),
            false, // is housing
            Housing(),
            inventoryItem
        )
    }
}
