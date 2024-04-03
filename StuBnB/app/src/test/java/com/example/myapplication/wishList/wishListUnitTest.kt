package com.example.myapplication.wishList

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

    @Test
    fun testClearAll() {
        WishList.addInventory(Inventory())
        WishList.addHousing(Housing())

        WishList.clearAll()

        assertTrue(WishList.inventories.isEmpty())
        assertTrue(WishList.housings.isEmpty())
    }

    @Test
    fun testIncludeInventory() {
        val inventory = Inventory()
        WishList.addInventory(inventory)

        assertTrue(WishList.includeInventory(inventory))
    }

    @Test
    fun testIncludeHousing() {
        val housing = Housing()
        WishList.addHousing(housing)

        assertTrue(WishList.includeHousing(housing))
    }

    @Test
    fun testAddDuplicateInventory() {
        val inventory = Inventory()
        WishList.addInventory(inventory)
        WishList.addInventory(inventory)

        assertEquals(1, WishList.inventories.size)
    }

    @Test
    fun testAddDuplicateHousing() {
        val housing = Housing()
        WishList.addHousing(housing)
        WishList.addHousing(housing)

        assertEquals(1, WishList.housings.size)
    }

    @Test
    fun testDeleteInventory() {
        val inventory = Inventory()
        WishList.addInventory(inventory)
        WishList.deleteInventory(inventory)

        assertFalse(WishList.includeInventory(inventory))
    }

    @Test
    fun testDeleteHousing() {
        val housing = Housing()
        WishList.addHousing(housing)
        WishList.deleteHousing(housing)

        assertFalse(WishList.includeHousing(housing))
    }
}
