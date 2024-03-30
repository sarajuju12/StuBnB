package com.example.myapplication

import com.example.myapplication.data.housing.HousingRepository
import com.example.myapplication.models.Housing
import org.junit.Assert.assertEquals
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
class HousingTest {

    @Test
    fun testDefault() {
        val housing = Housing()
        assertEquals("", housing.name)
        assertEquals(0.0, housing.price, 0.0)
    }

    @Test
    fun `test parameterized constructor`() {
        val imageLinks = listOf("link1", "link2")
        val housing = Housing(
            name = "TestName",
            description = "TestDescription",
            imageLinks = imageLinks,
            userId = "TestUserId",
            price = 100.0,
            startDate = "2024-01-01",
            endDate = "2024-01-31",
            address = "TestAddress",
            propertyType = "TestType",
            genderRestriction = "None",
            numOfGuests = 4,
            numOfBedrooms = 2,
            numOfBathrooms = 1,
            timeStamp = "2024-03-30"
        )

        assertEquals("TestName", housing.name)
        assertEquals("TestDescription", housing.description)
        assertEquals(imageLinks, housing.imageLinks)
        assertEquals("TestUserId", housing.userId)
        assertEquals(100.0, housing.price, 0.0)
    }

    @Test
    fun testEqual() {
        val housing1 = Housing()
        val housing2 = Housing()

        assertEquals(housing1, housing2)
    }

}
