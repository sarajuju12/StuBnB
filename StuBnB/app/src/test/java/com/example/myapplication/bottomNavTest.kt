package com.example.myapplication

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import org.junit.Assert.*
import org.junit.Test
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.screens.BottomNavigationItem
import com.example.myapplication.screens.BottomNavigationList

class bottomNavTest {
    @Test
    fun bottomNavigationItem() {
        val title = "Test"
        val selectedIcon = Icons.Filled.Home
        val unselectedIcon = Icons.Outlined.Home
        val hasNews = mutableStateOf(false)
        val badgeCount = mutableStateOf(0)

        val bottomNavItem = BottomNavigationItem(title, selectedIcon, unselectedIcon, hasNews, badgeCount)

        assertEquals(title, bottomNavItem.title)
        assertEquals(selectedIcon, bottomNavItem.selectedIcon)
        assertEquals(unselectedIcon, bottomNavItem.unselectedIcon)
        assertEquals(hasNews, bottomNavItem.hasNews)
        assertEquals(badgeCount, bottomNavItem.badgeCount)
    }

    @Test
    fun listTest() {
        val bottomNavItems = BottomNavigationList.Blist
        assertEquals(bottomNavItems.size, 5)
        assertEquals(bottomNavItems[2].title, "Wishlist")
        assertEquals(bottomNavItems[3].unselectedIcon, Icons.Outlined.Email)
        assertEquals(bottomNavItems[4].selectedIcon, Icons.Filled.Person)
    }
}
