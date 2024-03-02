package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.models.Housing
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.routers.*


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
        }
    }

    if (selectedIndex >= 0) {
        Navigator.navigate(Screen.House(housings[selectedIndex])) // navigator is an object
    }
}

@Composable
fun HousingItem(housing: Housing, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick) // click listener
    ) {
        // Image
        Image(
            painter = painterResource (id = R.drawable.picture), // Placeholder image
            contentDescription = "Housing Picture",
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Inventory Details
        Column {
            Text(text = housing.name, fontSize = 20.sp)
            Text(text = housing.description, fontSize = 14.sp)
            Text(text = housing.userId, fontSize = 14.sp)
        }
    }
}





