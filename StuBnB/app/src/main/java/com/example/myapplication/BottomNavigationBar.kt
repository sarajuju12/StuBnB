package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)


@Composable
fun BottomNavigationBar (items: List<BottomNavigationItem>,
                         selectedIndex: Int,
                         onItemSelected: (Int) -> Unit ) {

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = (selectedIndex == index),  // true if index is selected
                onClick = { onItemSelected(index) },
                label = { Text(text = item.title) },
                alwaysShowLabel = true,     // easier to access
                icon = { NavigationBarItem(item, index == selectedIndex) }
            )
        }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun NavigationBarItem(item: BottomNavigationItem, isSelected: Boolean) {
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
    val items = getBottomNavigationItems()
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
        Scaffold(
            bottomBar = {
                BottomNavigationBar( items = items, selectedIndex = selectedItemIndex,
                                     onItemSelected = { index -> selectedItemIndex = index}
                )
            }
        ) {
            innerPadding -> MainContent(selectedItemIndex, innerPadding)
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

}

@Composable
fun InventoryScreen() {

}

@Composable
fun WishScreen() {

}

@Composable
fun InboxScreen() {

}

@Composable
fun ProfileScreen() {

}

