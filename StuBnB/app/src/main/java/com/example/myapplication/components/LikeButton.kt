package com.example.myapplication.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.data.LoginViewModel
import com.example.myapplication.data.wishList.UploadWishListEvent
import com.example.myapplication.data.wishList.UploadWishListViewModel
import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import com.example.myapplication.models.WishList
import com.example.myapplication.screens.BottomNavigationList

@Composable
fun DisplayHeartButton  (
    modifier: Modifier = Modifier,
    isHousing: Boolean,
    house: Housing,
    inventory: Inventory,
    UploadWishListViewModel: UploadWishListViewModel = UploadWishListViewModel()
) {
    val email = LoginViewModel.getEncryptedEmail()
    var iconState by remember { mutableStateOf(Icons.Outlined.Favorite) }

    LaunchedEffect(key1 = house, key2 = inventory) {
        iconState = if (isHousing) {
            if (WishList.includeHousing(house)) Icons.Filled.Favorite else Icons.Outlined.Favorite
        } else {
            if (WishList.includeInventory(inventory)) Icons.Filled.Favorite else Icons.Outlined.Favorite
        }
    }

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

                var event = UploadWishListEvent.updateWishlistHousing(house)
                UploadWishListViewModel.onEvent(event)

                isFavourite = !isFavourite
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

                var event = UploadWishListEvent.updateWishlistInventory(inventory)
                UploadWishListViewModel.onEvent(event)

                isFavourite = !isFavourite
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

