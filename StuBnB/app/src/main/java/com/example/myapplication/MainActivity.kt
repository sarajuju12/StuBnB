package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
=======
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.InventoryRepository
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.models.Inventory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // BELOW is the code to run the Inventory Listings!
                    // val tempInventoryRepository = InventoryRepository();
                    // InventoryList(tempInventoryRepository.getInventory())


                    //Greeting("Andriod")
                }*/
                MainScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.background(Color.Red),
        color = Color.Blue,
        fontSize = 30.sp
    )
}

@Composable
fun InventoryList(inventories: List<Inventory>) {
    LazyColumn {
        items(inventories.size) { index ->
            val inventory = inventories[index]
            InventoryItem(inventory = inventory)
        }
    }
}

@Composable
fun InventoryItem(inventory: Inventory) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Image
        Image(
            painter = painterResource(id = R.drawable.anchor), // Placeholder image
            contentDescription = "Inventory Picture",
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Inventory Details
        Column {
            Text(text = inventory.name, fontSize = 20.sp)
            Text(text = inventory.description, fontSize = 14.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val tempInventoryRepository = InventoryRepository();
    MyApplicationTheme {
        //Greeting("Android")
        InventoryList(tempInventoryRepository.getInventory())
    }
}

fun MainScreen() {
    DisplayBottomBar()
}

