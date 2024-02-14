package com.example.myapplication.views


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.components.*
import com.example.myapplication.data.CreateAccountEvent
import com.example.myapplication.data.CreateAccountViewModel
import com.example.myapplication.data.InventoryRepository
import com.example.myapplication.models.Inventory
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen

@Composable
fun UploadInventory() {

    var name = ""
    var userId = "benwen2" //placeholder
    var description = ""
    var imageLinks = mutableListOf("picture.png") //placeholder
    var price = 0
    var subject = ""
    var category = ""
    var condition = ""

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize().background(Color.White).padding(30.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TitleText(value = "Add Your Inventory Info")
                TextField(labelValue = "Item Name", painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        name = it
                    }
                )
                TextField(labelValue = "Description", painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        description = it
                    }
                )
                /*TextField(labelValue = "Item Name", painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        imageLinks = it
                    }
                )*/
                TextField(labelValue = "Price", painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        price = it.toInt()
                    }
                )
                TextField(labelValue = "Subject", painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        subject = it
                    }
                )
                TextField(labelValue = "Category", painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        category = it
                    }
                )
                TextField(labelValue = "Condition", painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        condition = it
                    }
                )

                Spacer(modifier = Modifier.height(50.dp))

                Button(
                    onClick = {
                        //println("ran")
                        val invRep = InventoryRepository()
                        val inventoryTemp = Inventory(
                             name, userId, description, imageLinks, price, subject, category, condition
                        )
                        invRep.createInventory(inventoryTemp)
                        Navigator.navigate(Screen.Home)
                    }
                ) {
                    Text(text = "UPLOAD INVENTORY LISTING")
                    //value = "UPLOAD INVENTORY LISTING",
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        Navigator.navigate(Screen.Home)
                    }
                ) {
                    Text(text = "BACK")
                    //value = "UPLOAD INVENTORY LISTING",
                }
                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }
}