package com.example.myapplication

import com.example.myapplication.models.*
import org.junit.Test
import org.junit.Assert.*

class WishListUnitTest {

    @Test
    fun testAddInventory() {
        val inventory = Inventory()
        WishList.addInventory(inventory)

        assertEquals(1, WishList.inventories.size)
        assertEquals(inventory, WishList.inventories.first())
    }

    @Test
    fun testAddHousing() {
        val housing = Housing()
        WishList.addHousing(housing)

        assertEquals(1, WishList.housings.size)
        assertEquals(housing, WishList.housings.first())
    }

}
