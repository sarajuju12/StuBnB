package com.example.myapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.components.ActionButton
import com.example.myapplication.data.*


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null // can have no badge
)


// get the bottom 5 tabs as a list
fun getBottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(  // todo badge count event
            title = "Housing",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Inventory",
            selectedIcon = Icons.Filled.Build,
            unselectedIcon = Icons.Outlined.Build,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Wishlist",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.Favorite,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Inbox",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = false,
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
                                        if (item.badgeCount != null) {
                                            Badge { Text(text = item.badgeCount.toString()) }
                                        } else if (item.hasNews) {
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
            innerPadding -> MainContent(selectedIndex, innerPadding)
            // pass in the index to jump to different tabs
        }
    }
}

@Composable
fun MainContent(selectedItemIndex: Int, innerPadding: PaddingValues) {
    when (selectedItemIndex) {
        0 -> HousingScreen()
        1 -> InventoryScreen()
        2 -> WishScreen()
        3 -> InboxScreen()
        4 -> ProfileScreen()
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
    val tempInventoryRepository = InventoryRepository();
    MyApplicationTheme {
        InventoryList(tempInventoryRepository.getInventory())
    }
}

@Composable
fun WishScreen() {

}

@Composable
fun InboxScreen() {

}

@Composable
fun ProfileScreen(homeViewModel: HomeViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        ActionButton(value = "LOG OUT", buttonClicked = { homeViewModel.logout() }, isEnabled = true)
    }
}

