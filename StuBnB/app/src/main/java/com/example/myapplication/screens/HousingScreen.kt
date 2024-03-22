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
import com.example.myapplication.models.Housing
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.example.myapplication.ui.theme.poppins
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HousingList(housings: List<Housing>) {

    var selectedIndex by rememberSaveable { mutableStateOf(-1) }
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    if (selectedIndex >= 0) {
        Navigator.navigate(Screen.House(housings[selectedIndex], true)) // navigator is an object
    }

    Scaffold {
        LazyColumn {
            items(housings.size) { index ->
                val housing = housings[index]
                if (text == "" || text.uppercase() in housing.name.uppercase() || text.uppercase() in housing.address.uppercase()
                    || text.uppercase() in housing.description.uppercase() || text in housing.startDate
                    || text in housing.endDate || text.uppercase() in housing.propertyType.uppercase()
                    || text.uppercase() in housing.genderRestriction.uppercase()) {
                    val onItemClick = {
                        selectedIndex = index
                    }

                    HousingItem(housing = housing, onClick = onItemClick)
                    if (index == housings.size - 1) {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
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
fun HousingItem(housing: Housing, onClick: () -> Unit, delete: Boolean = false) {
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        val imageUrl = housing.imageLinks.firstOrNull()
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
                // Image
                Image(
                    painter = painter,
                    contentDescription = "Inventory Picture",
                    modifier = Modifier.size(400.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        val oldDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val newDateFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())
                        Text(
                            text = "$%.2f".format(housing.price),
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = newDateFormat.format(oldDateFormat.parse(housing.startDate)) + " - " + newDateFormat.format(
                                oldDateFormat.parse(housing.endDate)
                            ),
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
                deleteHousing(housing.userId, housing.name, housing.timeStamp)
                showDeleteConfirmationDialog = false
             },
            dialogTitle = "Are you sure you want to delete this listing?",
            dialogText = "This item will be deleted immediately. You can't undo this action.",
            textConfirm = "Delete",
            textDismiss = "Cancel"
        )
    }
}

private fun deleteHousing(userId:String, listingId: String, timeStamp: String) {
    val database = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.getReference("housing").child(userId).child("${listingId}_${timeStamp}")
    myRef.removeValue()
        .addOnSuccessListener {
            Navigator.navigate(Screen.HomeHousing)
        }
        .addOnFailureListener {

        }
}
