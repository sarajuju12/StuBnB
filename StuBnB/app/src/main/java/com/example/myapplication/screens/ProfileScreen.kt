package com.example.myapplication.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.components.ActionButton
import com.example.myapplication.data.HomeViewModel
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen

@Composable
fun DisplayProfileScreen(homeViewModel: HomeViewModel = viewModel()) {

    var showDialog by remember { mutableStateOf(false)}


    if (showDialog) {
        UploadListingPopup(onDismiss = {showDialog=false})
    }

    Surface(
        modifier = Modifier.fillMaxSize().padding(30.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ActionButton(value = "UPLOAD A LISTING", buttonClicked = { showDialog = true }, isEnabled = true)
                Spacer(modifier = Modifier.height(50.dp))
                ActionButton(value = "LOG OUT", buttonClicked = { homeViewModel.logout() }, isEnabled = true)
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