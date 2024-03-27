package com.example.myapplication.views.detailPages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.myapplication.components.BackButton
import com.example.myapplication.components.DisplayHeartButton
import com.example.myapplication.models.Housing
import com.example.myapplication.routers.Hos
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Prof
import com.example.myapplication.routers.Screen
import com.example.myapplication.ui.theme.Purple40
import com.example.myapplication.ui.theme.poppins


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun House(housingItem: Housing, fromHos: Int) {
    val pagerState = remember { PagerState() }
    val pageCount = housingItem.imageLinks.size

    Column {
        HorizontalPager(
            state = pagerState,
            pageCount = housingItem.imageLinks.size,
            modifier = Modifier.fillMaxWidth().height(200.dp)
        ) { page ->
            val imageUrl = housingItem.imageLinks[page]
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Item image",
                modifier = Modifier
                    .fillMaxWidth().aspectRatio(1.5f), // Adjust the aspect ratio as needed to maintain the image's width-to-height ratio
                contentScale = ContentScale.Crop
            )
        }
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pageCount) { iteration ->
                        val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(16.dp)
                        )
                    }
                }
            }

            item {
                Text(text = "University: ${housingItem.name}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Property Type: ${housingItem.propertyType}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Description: ${housingItem.description}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Address: ${housingItem.address}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Gender Restrictions: ${housingItem.genderRestriction}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Price: ${"$%.2f".format(housingItem.price)}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Rental Period: ${housingItem.startDate} - ${housingItem.endDate}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Number of Guests: ${housingItem.numOfGuests}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Number of Bedrooms: ${housingItem.numOfBedrooms}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Number of Bathrooms: ${housingItem.numOfBathrooms}")
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Send host a message", modifier = Modifier.padding(start = 5.dp), fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(5.dp)
                    ) {
                        TextField(
                            value = "Hi, is this still available?",
                            onValueChange = { },
                            enabled = false,
                            label = { },
                            singleLine = true
                        )
                        Button(
                            onClick = {
                                // send host a message here
                            },
                            enabled = true,
                            colors = ButtonDefaults.buttonColors(Purple40),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("SEND")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BackButton(
            buttonClicked = {
                if (fromHos == Hos) Navigator.navigate(Screen.HomeHousing)
                else if (fromHos == Prof) Navigator.navigate(Screen.HomeProfile)
                else Navigator.navigate(Screen.HomeWishlist)
            },
            modifier = Modifier
                .size(78.dp)
                .align(Alignment.TopStart)
        )
        DisplayHeartButton(modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(end = 16.dp, top = 16.dp)
            .size(36.dp),
            true, // is housing
            housingItem,
            com.example.myapplication.models.Inventory()
        )
    }
}
