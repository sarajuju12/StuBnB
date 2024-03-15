package com.example.myapplication.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.data.housing.UploadHousingEvent
import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import com.example.myapplication.models.WishList
import com.example.myapplication.data.housing.UploadHousingViewModel
import com.example.myapplication.data.inventory.UploadInventoryEvent
import com.example.myapplication.data.inventory.UploadInventoryViewModel
import com.example.myapplication.routers.Screen


@Composable
fun DisplayHeartButton(
    modifier: Modifier = Modifier,
    isHousing: Boolean,
    house: Housing,
    inventory: Inventory
) {

    val (icon, setIcon) = remember {
        mutableStateOf(
            if (isHousing && house.favourite || !isHousing && inventory.favourite) {
                Icons.Filled.Favorite
            } else {
                Icons.Outlined.Favorite
            }
        )
    }

    IconButton(
        onClick = {
            if (isHousing) {
                if (house.favourite){
                    WishList.deleteHousing(house)
                } else {
                    WishList.addHousing((house))
                }

                house.favourite = !house.favourite

                // upload the changes to the database
                val model = UploadHousingViewModel()
                model.onEvent(UploadHousingEvent.FavouriteChange(house))

                setIcon(if (house.favourite) Icons.Filled.Favorite else Icons.Outlined.Favorite)
            } else {
                if (inventory.favourite){
                    WishList.deleteInventory(inventory)
                } else {
                    WishList.addInventory(inventory)
                }

                inventory.favourite = !inventory.favourite

                // upload the changes to the database
                val model = UploadInventoryViewModel()
                model.onEvent(UploadInventoryEvent.FavouriteChange(inventory))

                setIcon(if (inventory.favourite) Icons.Filled.Favorite else Icons.Outlined.Favorite)
            }
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Heart",
            tint = if (icon == Icons.Filled.Favorite) Color.Red else Color.Gray
        )
    }
}

