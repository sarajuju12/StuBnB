package com.example.myapplication.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.HomeViewModel
import com.example.myapplication.data.LoginViewModel
import com.example.myapplication.data.housing.HousingCallback
import com.example.myapplication.data.housing.HousingRepository
import com.example.myapplication.data.repositories.InventoryCallback
import com.example.myapplication.data.repositories.InventoryRepository
import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import com.example.myapplication.models.WishList
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.poppins
import com.example.myapplication.views.Inbox
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: MutableState<Boolean>,
    val badgeCount: MutableState<Int> 
)

object BottomNavigationList{
    val Blist = getBottomNavigationItems()
}

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
            hasNews = mutableStateOf(false),
            badgeCount = mutableStateOf(0)
        ),
        BottomNavigationItem(
            title = "Inbox",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            hasNews = mutableStateOf(false),
            badgeCount = mutableStateOf(0)
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
fun DisplayBottomBar(starter: Int){
    // call to get the list of bottom navigation items
    val items = getBottomNavigationItems()
    // *** selectedIndex hold the current selected index, changes when the status changes
    // *** initially at state 0, the housing tab
    var selectedIndex by rememberSaveable { mutableStateOf(starter) }

    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->   // loop over the navigation items
                    var isSelected = selectedIndex == index

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedIndex = index },
                        label = { Text(text = item.title, fontFamily = poppins, fontWeight = FontWeight.Normal) },
                        alwaysShowLabel = true,     // easier to visualize
                        icon = {
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
    }
}

fun retrieveWishlistData(){
    val email = LoginViewModel.getEncryptedEmail()
    val safeEmail = email.replace(".", ",")
    val housingRef = FirebaseDatabase.getInstance().getReference("wishlist/$safeEmail/housing")

    housingRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (houseSnapshot in dataSnapshot.children) {
                val house = houseSnapshot.getValue(Housing::class.java)
                house?.let {
                    WishList.addHousing(it)
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
        }
    })

    // retrieve wishlist inventory data
    val inventoryRef = FirebaseDatabase.getInstance().getReference("wishlist/$safeEmail/inventory")

    inventoryRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (inventorySnapshot in dataSnapshot.children) {
                val inventory = inventorySnapshot.getValue(Inventory::class.java)
                inventory?.let {
                    WishList.addInventory(it)
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
        }
    })
}

@Composable
fun MainContent(selectedItemIndex: Int, innerPadding: PaddingValues, items: List<BottomNavigationItem>) {
    when (selectedItemIndex) {
        0 -> {
            retrieveWishlistData()
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

// has database now
@Composable
fun HousingScreen() {
    val tempHousingRepository = HousingRepository()
    val listOfHousing = remember { mutableStateOf<List<Housing>>(emptyList()) }

    // Fetch inventory data when the screen is first displayed or recomposed
    LaunchedEffect(key1 = true) {
        // does not contribute to UI
        tempHousingRepository.getHousing(object : HousingCallback {
            override fun onHousingLoaded(housingList: MutableList<Housing>) {
                // Update the state with the new inventory data
                listOfHousing.value = housingList
            }
        })
    }

    MyApplicationTheme {
        HousingList(listOfHousing.value)
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
    WishListScreen()
}

@Composable
fun InboxScreen() {
    MyApplicationTheme {
        Inbox()
    }
}

@Composable
fun ProfileScreen(homeViewModel: HomeViewModel = viewModel()) {
    DisplayProfileScreen(homeViewModel)
}

