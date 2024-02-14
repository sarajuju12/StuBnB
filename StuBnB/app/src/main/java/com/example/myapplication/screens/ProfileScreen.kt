package com.example.myapplication.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.views.UploadInventory

@Composable
fun DisplayProfileScreen() {

    var showDialog by remember { mutableStateOf(false)}


    if (showDialog) {
        UploadListingPopup(onDismiss = {showDialog=false})
    }

    Surface(modifier = Modifier.fillMaxSize()) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()) {

            Button(onClick = { showDialog=true }) {
                Text("Upload a listing")

            }
        }
    }

}

@Composable
fun UploadListingPopup(
    onDismiss:()->Unit
) {

    //var selectedIndex by rememberSaveable { mutableStateOf(0) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { /*TODO*/ },
        modifier = Modifier.height(250.dp),

        text = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { /*selectedIndex = 1 */},
                    modifier = Modifier
                        .width(180.dp)
                        .padding(10.dp)
                ) {
                    Text(text = "Housing Listing")
                }

                Button(
                    onClick = { Navigator.navigate(Screen.UploadInventory) },
                    modifier = Modifier
                        .width(180.dp)
                        .padding(10.dp)
                ) {
                    Text(text = "Inventory Listing")
                }
            }
        }
    )
}
/*
@Composable
fun MainContent(selectedItemIndex: Int) {
    when (selectedItemIndex) {
        1 -> UploadHousingScreen()
        2 -> UploadInventoryScreen()
    }
}

 */
@Composable
fun UploadHousingScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Gray
    ) {

    }
}

@Composable
fun UploadInventoryScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Gray
    ) {

    }
}