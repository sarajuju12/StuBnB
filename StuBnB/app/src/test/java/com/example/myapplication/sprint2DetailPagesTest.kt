package com.example.myapplication

import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 * cd /Users/daoyuanyang/Desktop/cs346/team-7/StuBnB/app/src/main/java/com/example/myapplication
 *
 * test
 * cd /Users/daoyuanyang/Desktop/cs346/team-7/StuBnB/app/src/test/java/com/example/myapplication
 *
 * need more unit test
 *
 */
class sprint2DetailPagesTest {
    // housing detail
    @Test
    fun housingConstructor() {
        val imageLinks = listOf("link1", "link2")
        val housing = Housing(
            name = "Test House",
            description = "A lovely test house",
            imageLinks = imageLinks,
            userId = "user123",
            price = 1000.0,
            startDate = "2024-01-01",
            endDate = "2024-01-10",
            address = "123 Test Street",
            propertyType = "Apartment",
            genderRestriction = "None",
            numOfGuests = 4,
            numOfBedrooms = 2,
            numOfBathrooms = 1,
            timeStamp = "1290383911"
        )

        assertEquals("Test House", housing.name)
        assertEquals("A lovely test house", housing.description)
        assertEquals(imageLinks, housing.imageLinks)
        assertEquals("user123", housing.userId)
        assertEquals(1000.0, housing.price, 0.0)
        assertEquals("2024-01-01", housing.startDate)
        assertEquals("2024-01-10", housing.endDate)
        assertEquals("123 Test Street", housing.address)
        assertEquals("Apartment", housing.propertyType)
        assertEquals("None", housing.genderRestriction)
        assertEquals(4, housing.numOfGuests)
        assertEquals(2, housing.numOfBedrooms)
        assertEquals(1, housing.numOfBathrooms)
    }

    @Test
    fun housingDefaultConstructor() {
        val housing = Housing()

        assertEquals("", housing.name)
        assertEquals("", housing.description)
        assertEquals(emptyList<String>(), housing.imageLinks)
        assertEquals("", housing.userId)
        assertEquals(0.0, housing.price, 0.0)
        assertEquals("", housing.startDate)
        assertEquals("", housing.endDate)
        assertEquals("", housing.address)
        assertEquals("", housing.propertyType)
        assertEquals("", housing.genderRestriction)
        assertEquals(0, housing.numOfGuests)
        assertEquals(0, housing.numOfBedrooms)
        assertEquals(0, housing.numOfBathrooms)
    }


    // inventory detail
    @Test
    fun testInventoryConstructor() {
        val imageLinks = listOf("imageLink1", "imageLink2")
        val inventory = Inventory(
            name = "Test Item",
            userId = "user456",
            description = "A description of the test item",
            imageLinks = imageLinks,
            price = 200.0,
            subject = "Test Subject",
            category = "Test Category",
            condition = "New",
            timeStamp = "1290383911"
        )

        assertEquals("Test Item", inventory.name)
        assertEquals("user456", inventory.userId)
        assertEquals("A description of the test item", inventory.description)
        assertEquals(imageLinks, inventory.imageLinks)
        assertEquals(200.0, inventory.price, 0.0)
        assertEquals("Test Subject", inventory.subject)
        assertEquals("Test Category", inventory.category)
        assertEquals("New", inventory.condition)
    }

    @Test
    fun testInventoryDefaultConstructor() {
        val inventory = Inventory()

        assertEquals("", inventory.name)
        assertEquals("", inventory.userId)
        assertEquals("", inventory.description)
        assertEquals(emptyList<String>(), inventory.imageLinks)
        assertEquals(0.0, inventory.price, 0.0)
        assertEquals("", inventory.subject)
        assertEquals("", inventory.category)
        assertEquals("", inventory.condition)
    }
}
