package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.models.Inventory
import com.example.myapplication.R


@Composable
fun InventoryList(inventories: List<Inventory>) {
    LazyColumn {
        items(inventories.size) { index ->
            val inventory = inventories[index]
            InventoryItem(inventory = inventory)
        }
    }
}

@Composable
fun InventoryItem(inventory: Inventory) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Image
        Image(
            painter = painterResource (id = R.drawable.anchor), // Placeholder image
            contentDescription = "Inventory Picture",
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Inventory Details
        Column {
            Text(text = inventory.name, fontSize = 20.sp)
            Text(text = inventory.description, fontSize = 14.sp)
        }
    }
}





