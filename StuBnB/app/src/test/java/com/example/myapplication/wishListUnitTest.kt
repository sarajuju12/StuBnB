package com.example.myapplication

import com.example.myapplication.models.*
import org.junit.Test
import org.junit.Assert.*

class wishListUnitTest {

    @Test
    fun testAddInventory() {
        val wishList = WishList()
        val inventory = Inventory()
        wishList.addInventory(inventory)

        assertEquals(1, wishList.inventories.size)
        assertEquals(inventory, wishList.inventories[0])
    }

    @Test
    fun testAddHousing() {
        val wishList = WishList()
        val housing = Housing()
        wishList.addHousing(housing)

        assertEquals(1, wishList.housings.size)
        assertEquals(housing, wishList.housings[0])
    }

    @Test
    fun testSecondaryConstructor() {
        val initialInventories = listOf(Inventory(),
            Inventory(
                name = "Test Item",
            userId = "user456",
            description = "A description of the test item",
            imageLinks = listOf("link1", "link2"),
            price = 200.0,
            subject = "Test Subject",
            category = "Test Category",
            condition = "New"
        ))
        val initialHousings = listOf(Housing(),
            Housing(
                name = "Test House",
                description = "A lovely test house",
                imageLinks = listOf("link1", "link2"),
                userId = "user123",
                price = 1000.0,
                startDate = "2024-01-01",
                endDate = "2024-01-10",
                address = "123 Test Street",
                propertyType = "Apartment",
                genderRestriction = "None",
                numOfGuests = 4,
                numOfBedrooms = 2,
                numOfBathrooms = 1
            ))

        val wishList = WishList(initialInventories, initialHousings, true)

        assertEquals(2, wishList.inventories.size)
        assertEquals(2, wishList.housings.size)
        assertEquals(initialInventories[0], wishList.inventories[0])
        assertEquals(initialInventories[1], wishList.inventories[1])
        assertEquals(initialHousings[0], wishList.housings[0])
        assertEquals(initialHousings[1], wishList.housings[1])
    }

}
