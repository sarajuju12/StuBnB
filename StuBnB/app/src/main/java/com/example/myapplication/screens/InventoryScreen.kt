package com.example.myapplication.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.myapplication.components.TwoFactorAuthentication
import com.example.myapplication.models.Inventory
import com.example.myapplication.routers.Hos
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.example.myapplication.ui.theme.poppins
import com.example.myapplication.views.SearchBar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/*
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventorySearch() {

}*/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryList(inventories: List<Inventory>) {

    var selectedIndex by rememberSaveable { mutableStateOf(-1) }



    if (selectedIndex >= 0) {
        Navigator.navigate(Screen.Inventory(inventories[selectedIndex], Hos)) // pass in the selected item
    }

    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold {
        LazyColumn {
            items(inventories.size) { index ->
                val inventory = inventories[index]
                if (text == "" || text.uppercase() in inventory.name.uppercase() || text.uppercase() in inventory.category.uppercase()
                    || text.uppercase() in inventory.condition.uppercase() || text.uppercase() in inventory.description.uppercase()
                    || text.uppercase() in inventory.subject.uppercase()) {
                    val onItemClick = {
                        selectedIndex = index
                    }
                    InventoryItem(inventory = inventory, onClick = onItemClick)
                }
            }
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = text,
            onQueryChange = {
                text = it
            },
            onSearch = {
                active = false
            },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (text.isNotEmpty()) {
                                text = ""
                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        ) {

        }
    }
}
@Composable
fun InventoryItem(inventory: Inventory, onClick: () -> Unit, delete: Boolean = false) {
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        val imageUrl = inventory.imageLinks.firstOrNull()
        val painter: Painter = rememberImagePainter(
            data = imageUrl,
            builder = {

            }
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Box(
                modifier = Modifier.height(400.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = "Inventory Picture",
                    modifier = Modifier.size(400.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Box (
                modifier = Modifier.fillMaxSize().padding(15.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = inventory.name,
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "$%.2f".format(inventory.price),
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Column {
                        if (delete) {
                            Button(
                                onClick = { showDeleteConfirmationDialog = true },
                                enabled = true,
                                colors = ButtonDefaults.buttonColors(Color.Red),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
    if (showDeleteConfirmationDialog) {
        TwoFactorAuthentication(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            onConfirmation = {
                deleteInventory(inventory.userId, inventory.name, inventory.timeStamp)
                showDeleteConfirmationDialog = false
             },
            dialogTitle = "Are you sure you want to delete this listing?",
            dialogText = "This item will be deleted immediately. You can't undo this action.",
            textConfirm = "Delete",
            textDismiss = "Cancel"
        )
    }
}

private fun deleteInventory(userId:String, listingId: String, timeStamp: String) {
    val database = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.getReference("inventory").child(userId).child("${listingId}_${timeStamp}")
    myRef.removeValue()
        .addOnSuccessListener {
            Navigator.navigate(Screen.HomeInventory)
        }
        .addOnFailureListener {

        }
}
