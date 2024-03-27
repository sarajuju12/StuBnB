package com.example.myapplication.views.detailPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.myapplication.components.BackButton
import com.example.myapplication.components.DisplayHeartButton
import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import com.example.myapplication.routers.Inv
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Prof
import com.example.myapplication.routers.Screen
import com.example.myapplication.ui.theme.Purple40
import com.example.myapplication.ui.theme.poppins


@Composable
fun Inventory(inventoryItem: Inventory, fromInv: Int) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        items(inventoryItem.imageLinks) { imageUrl ->
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Item image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Text(text = "Category: ${inventoryItem.category}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Condition: ${inventoryItem.condition}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Subject: ${inventoryItem.subject}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Description: ${inventoryItem.description}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Price: ${"$%.2f".format(inventoryItem.price)}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Item Name: ${inventoryItem.name}")
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Send seller a message", modifier = Modifier.padding(start = 5.dp), fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold)
                Row (
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                ) {
                    TextField(
                        value = "Hi, is this still available?",
                        onValueChange = { },
                        enabled = false,
                        label = { },
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            // send host a message here
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
