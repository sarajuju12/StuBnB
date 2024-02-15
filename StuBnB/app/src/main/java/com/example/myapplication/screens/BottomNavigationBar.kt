package com.example.myapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.components.ActionButton
import com.example.myapplication.data.*
import com.example.myapplication.models.Inventory
import com.example.myapplication.views.UploadInventory


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: MutableState<Boolean>,
    val badgeCount: MutableState<Int> // can have no badge
)


// get the bottom 5 tabs as a list
fun getBottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(  // todo badge count event
            title = "Housing",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = mutableStateOf(false),
            badgeCount = mutableStateOf(0)
        ),
        BottomNavigationItem(
            title = "Inventory",
            selectedIcon = Icons.Filled.Build,
            unselectedIcon = Icons.Outlined.Build,
            hasNews = mutableStateOf(false),
            badgeCount = mutableStateOf(0)
        ),
        BottomNavigationItem(
            title = "Wishlist",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.Favorite,
            hasNews = mutableStateOf(true), // for sprint 1 demo
            badgeCount = mutableStateOf(0)
        ),
        BottomNavigationItem(
            title = "Inbox",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            hasNews = mutableStateOf(false),
            badgeCount = mutableStateOf(3) // for sprint 1 demo
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = mutableStateOf(false),
            badgeCount = mutableStateOf(0)
        ),
    )
}

// creates the bottom navigation bar
@Composable
fun DisplayBottomBar(){
    // call to get the list of bottom navigation items
    val items = getBottomNavigationItems()
    // *** selectedIndex hold the current selected index, changes when the status changes
    // *** initially at state 0, the housing tab
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        // isSelected indicate if this tab is selected at the moment
                        var isSelected = selectedIndex == index

                        NavigationBarItem(
                            selected = isSelected,  // true if index is selected
                            onClick = { selectedIndex = index },
                            label = { Text(text = item.title) },
                            alwaysShowLabel = true,     // easier to visualize

                            icon = {
                                //      display badge
                                //      select the button
                                //      reference : https://www.composables.com/material3/badgedbox
                                BadgedBox(
                                    badge = {
                                        if (item.badgeCount.value != 0) {
                                            Badge { Text(text = item.badgeCount.value.toString()) }
                                        } else if (item.hasNews.value) {
                                            Badge()
                                        }
                                    }
                                ) {// content
                                    Icon(
                                        imageVector = if (isSelected) {
                                            item.selectedIcon
                                        } else {
                                            item.unselectedIcon
                                        },
                                        contentDescription = item.title
                                    )
                                }
                            }
                        )
                    }
                }
            }
        ) {
            innerPadding -> MainContent(selectedIndex, innerPadding, items)
            // pass in the index to jump to different tabs
        }
    }
}

@Composable
fun MainContent(selectedItemIndex: Int, innerPadding: PaddingValues, items: List<BottomNavigationItem>) {
    when (selectedItemIndex) {
        0 -> {
            HousingScreen()
            items[0].hasNews.value = false
            items[0].badgeCount.value = 0
        }
        1 -> {
            InventoryScreen()
            items[1].hasNews.value = false
            items[1].badgeCount.value = 0
        }

        2 -> {
            WishScreen()
            items[2].hasNews.value = false
            items[2].badgeCount.value = 0
        }
        3 -> {
            InboxScreen()
            items[3].hasNews.value = false
            items[3].badgeCount.value = 0
        }
        4 -> {
            ProfileScreen()
            items[4].hasNews.value = false
            items[4].badgeCount.value = 0
        }
    }
}

@Composable
fun HousingScreen() {
    val tempHousingRepository = HousingRepository()
    MyApplicationTheme {
        HousingList(tempHousingRepository.getHousing())
    }
}

@Composable
fun InventoryScreen() {
    val tempInventoryRepository = InventoryRepository()
    val listOfInventory = remember { mutableStateOf<List<Inventory>>(emptyList()) }

    // Fetch inventory data when the screen is first displayed or recomposed
    LaunchedEffect(key1 = true) {
        tempInventoryRepository.getInventory(object : InventoryCallback {
            override fun onInventoryLoaded(inventoryList: MutableList<Inventory>) {
                // Update the state with the new inventory data
                listOfInventory.value = inventoryList
            }
        })
    }

    MyApplicationTheme {
        InventoryList(listOfInventory.value)
    }
}

@Composable
fun WishScreen() {

}

@Composable
fun InboxScreen() {
    /*
    MyApplicationTheme {
        UploadInventory()
    }
    */
}

@Composable
fun ProfileScreen(homeViewModel: HomeViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        ActionButton(value = "LOG OUT", buttonClicked = { homeViewModel.logout() }, isEnabled = true)
    }
    DisplayProfileScreen()
}

