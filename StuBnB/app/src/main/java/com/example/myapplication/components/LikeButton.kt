package com.example.myapplication.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.LoginViewModel
import com.example.myapplication.data.housing.UploadHousingEvent
import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import com.example.myapplication.models.WishList
import com.example.myapplication.data.housing.UploadHousingViewModel
import com.example.myapplication.data.inventory.UploadInventoryEvent
import com.example.myapplication.data.inventory.UploadInventoryViewModel
import com.example.myapplication.routers.Screen
import com.example.myapplication.screens.BottomNavigationList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

fun updateWishlistInventory(email: String, inventory: Inventory) {
    val safeEmail = email.replace(".", ",")

    val inventoryListRef = FirebaseDatabase.getInstance().getReference("wishlist")
        .child(safeEmail).child("inventory")

    val inventoryItemRef = inventoryListRef.child(inventory.name.trim())

    inventoryItemRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                // If the inventory item exists, delete it from the wishlist
                inventoryItemRef.removeValue()
            } else {
                inventoryItemRef.setValue(inventory)
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
        }
    })
}


fun updateWishlistHousing(email: String, house: Housing) {
    val safeEmail = email.replace(".", ",")

    val ref = FirebaseDatabase.getInstance().getReference("wishlist")
        .child(safeEmail).child("housing").child(house.name.trim())

    // Check if the house already exists in the wishlist
    ref.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                ref.removeValue()
            } else {
                ref.setValue(house)
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
        }
    })
}

@Composable
fun DisplayHeartButton  (
    modifier: Modifier = Modifier,
    isHousing: Boolean,
    house: Housing,
    inventory: Inventory
) {
    val email = LoginViewModel.getEncryptedEmail()
    var iconState by remember { mutableStateOf(Icons.Outlined.Favorite) }

    IconButton(
        onClick = {
            var isFavourite = if (isHousing) {
                WishList.includeHousing(house)
            } else {
                WishList.includeInventory(inventory)
            }

            if (isHousing) {
                if (isFavourite){
                    WishList.deleteHousing(house)
                    if (BottomNavigationList.Blist[2].badgeCount.value > 0)
                        BottomNavigationList.Blist[2].badgeCount.value--
                } else {
                    WishList.addHousing((house))
                    BottomNavigationList.Blist[2].badgeCount.value++
                }

                isFavourite = !isFavourite
                updateWishlistHousing(email, house)
                iconState = if (isFavourite) Icons.Filled.Favorite else Icons.Outlined.Favorite
            } else {
                if (isFavourite){
                    WishList.deleteInventory(inventory)
                    if (BottomNavigationList.Blist[2].badgeCount.value > 0)
                        BottomNavigationList.Blist[2].badgeCount.value--
                } else {
                    WishList.addInventory(inventory)
                    BottomNavigationList.Blist[2].badgeCount.value++
                }

                isFavourite = !isFavourite
                updateWishlistInventory(email, inventory)
                iconState = if (isFavourite) Icons.Filled.Favorite else Icons.Outlined.Favorite
            }
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = iconState,
            contentDescription = "Heart",
            tint = if (iconState == Icons.Filled.Favorite) Color.Red else Color.Gray
        )
    }
}

