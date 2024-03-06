package com.example.myapplication.views.detailPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.myapplication.components.BackButton
import com.example.myapplication.routers.*
import com.example.myapplication.models.Housing


@Composable
fun House(HousingItem: Housing) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        items(HousingItem.imagelink) { imageUrl ->
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Item image", // provide proper content description
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Set the height you want
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Text(text = "Category: ${HousingItem.description}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Condition: ${HousingItem.address}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Subject: ${HousingItem.genderRestriction}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Price: ${"$%.2f".format(HousingItem.price)}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Item Name: ${HousingItem.startDate} - ${HousingItem.endDate}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Seller: ${HousingItem.userId}".replace(",", "."))
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BackButton(
            buttonClicked = {
                Navigator.navigate(Screen.HomeHousing)
            },
            modifier = Modifier
                .size(78.dp)
                .align(Alignment.TopStart)
        )
    }
}