package com.example.myapplication

import com.example.myapplication.models.Date
import org.junit.Test
import com.example.myapplication.models.*

import org.junit.Assert.*

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
class HousingUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    val startDate = Date(2019, 1, 28)
    val endDate = Date(2024, 2, 4)

    val house = Housing(
        name = "House",
        description = "Description for House",
        imageLinks = mutableListOf("link1a", "link1b"), // links

        seller = "cs346",
        price = 14,
        startDate = startDate,
        endDate = endDate,
    )

    @Test
    fun testHousing() {
        assertEquals(house.seller, "cs346") // just run current file
    }

}
