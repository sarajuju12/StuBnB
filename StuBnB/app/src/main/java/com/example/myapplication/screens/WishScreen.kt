package com.example.myapplication.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.LoginViewModel
import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import com.example.myapplication.models.WishList
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

fun retriveWishlistData(){
    val email = LoginViewModel.getEncryptedEmail()
    val safeEmail = email.replace(".", ",")
    val housingRef = FirebaseDatabase.getInstance().getReference("wishlist/$safeEmail/housing")

    housingRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (houseSnapshot in dataSnapshot.children) {
                val house = houseSnapshot.getValue(Housing::class.java)
                house?.let {
                    WishList.addHousing(it)
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
        }
    })

    // retrieve wishlist inventory data
    val email2 = LoginViewModel.getEncryptedEmail()
    val safeEmail2 = email2.replace(".", ",")
    val inventoryRef = FirebaseDatabase.getInstance().getReference("wishlist/$safeEmail2/inventory")

    inventoryRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (inventorySnapshot in dataSnapshot.children) {
                val inventory = inventorySnapshot.getValue(Inventory::class.java)
                inventory?.let {
                    WishList.addInventory(it)
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
        }
    })
}

@Composable
fun WishListScreen() {
        retriveWishlistData()

        val housings = WishList.housings
        val inventories = WishList.inventories

        var selectedIndex by rememberSaveable { mutableStateOf(-1) }
        var Sindex by rememberSaveable { mutableStateOf(-1) }

        LazyColumn {
            items(inventories.size) { index ->
                val inventory = inventories[index]

                val onItemClick = {
                    selectedIndex = index
                }

                InventoryItem(inventory = inventory, onClick = onItemClick)
            }

            items(housings.size) { index ->
                val housing = housings[index]

                val onItemClick = {
                    Sindex = index
                }

                HousingItem(housing = housing, onClick = onItemClick)
                if (index == housings.size - 1) {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }

        if (selectedIndex >= 0) {
            Navigator.navigate(Screen.Inventory(inventories[selectedIndex], false)) // pass in the selected item
        }

        if (Sindex >= 0) {
            Navigator.navigate(Screen.House(housings[Sindex], false)) // navigator is an object
        }

}
