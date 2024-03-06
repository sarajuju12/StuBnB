package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.myapplication.models.Housing
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.example.myapplication.ui.theme.poppins


@Composable
fun HousingList(housings: List<Housing>) {

    var selectedIndex by rememberSaveable { mutableStateOf(-1) }

    LazyColumn {
        items(housings.size) { index ->
            val housing = housings[index]

            val onItemClick = {
                selectedIndex = index
            }

            HousingItem(housing = housing, onClick = onItemClick)
            if (index == housings.size - 1) {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }

    if (selectedIndex >= 0) {
        Navigator.navigate(Screen.House) // navigator is an object

    }
}

@Composable
fun HousingItem(housing: Housing, onClick: () -> Unit) {
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
                modifier = Modifier.fillMaxSize().padding(15.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Text(text = "$%.2f".format(housing.price), color = Color.Black, fontSize = 20.sp, fontFamily = poppins, fontWeight = FontWeight.SemiBold)
                    Text(text = housing.startDate + " - " + housing.endDate, color = Color.Black, fontSize = 16.sp, fontFamily = poppins, fontWeight = FontWeight.SemiBold)
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}





