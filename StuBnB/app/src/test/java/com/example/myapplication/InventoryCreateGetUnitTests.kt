package com.example.myapplication

import com.example.myapplication.data.repositories.InventoryRepository
import com.example.myapplication.models.Inventory
import org.junit.Test
import org.junit.Assert.*


class InventoryCreateGetUnitTest {
    val temporaryInventory = Inventory(
        "item-335543641",
        "user-335543641",
        "Description of Item 2",
        mutableListOf("link1", "link2", "link3"),
        100.0 * 3,
        "Subject 2",
        "Category 2",
        "Condition 2"
    )

    val invRepo = InventoryRepository();

    @Test
    fun testInventoryData() {
        assertEquals(temporaryInventory.name, "item-335543641")
        assertEquals(temporaryInventory.userId, "user-335543641")
        assertEquals(temporaryInventory.description, "Description of Item 2")
        assertEquals(temporaryInventory.price, 300)
        assertEquals(temporaryInventory.imageLinks[0], "link1")
        assertEquals(temporaryInventory.imageLinks[1], "link2")
        assertEquals(temporaryInventory.imageLinks[2], "link3")
        assertEquals(temporaryInventory.subject, "Subject 2")
        assertEquals(temporaryInventory.category, "Category 2")
        assertEquals(temporaryInventory.condition, "Condition 2")
    }

//    @Test
//    fun testGetInventoryByUserID() {
//        val tempInventoryRepository = InventoryRepository()
//        tempInventoryRepository.createInventory(temporaryInventory)
//
//       tempInventoryRepository.getInventoryOfUser(object : InventoryCallback {
//            override fun onInventoryLoaded(inventoryList: MutableList<Inventory>) {
//                // Update the state with the new inventory data
//                assertEquals(inventoryList[0].userId, "user-335543641")
//            }
//        }, "user-335543641")
//    }

}