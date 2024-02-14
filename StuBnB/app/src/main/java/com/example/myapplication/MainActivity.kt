package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.routers.Router
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Router()
        }

        //        val temporaryInventory = Inventory(
//            name = "item-2",
//            userId = "user-2",
//            description = "Description of Item 2",
//            imageLinks = mutableListOf("link1", "link2", "link3"),
//            price = 100 * 3,
//            subject = "Subject 2",
//            category = "Category 2",
//            condition = "Condition 2"
//        )
//
//        val invRepos = InventoryRepository();
//
//        invRepos.createInventory(temporaryInventory)
//        invRepos.createInventory(temporaryInventory)
    }
}