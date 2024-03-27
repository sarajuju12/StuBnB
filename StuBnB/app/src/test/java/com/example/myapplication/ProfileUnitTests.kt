package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import com.example.myapplication.models.User
import com.google.firebase.database.FirebaseDatabase
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

open class FirebaseDatabaseWrapper {
    open fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(userId, name, email)
        FirebaseDatabase.getInstance().getReference("users").child(userId).setValue(user)
    }
}

class ProfileManager(private val firebaseDatabaseWrapper: FirebaseDatabaseWrapper) {
    fun createProfile(userId: String, name: String, email: String) {
        firebaseDatabaseWrapper.writeNewUser(userId, name, email)
    }
}

class ProfileUnitTests {

    private val mockFirebaseDatabaseWrapper = mockk<FirebaseDatabaseWrapper>()
    private val profileManager = ProfileManager(mockFirebaseDatabaseWrapper)

    @Test
    fun createProfile() {
        val userId = "123456"
        val name = "John Doe"
        val email = "john@example.com"
        every { mockFirebaseDatabaseWrapper.writeNewUser(userId, name, email) } just Runs
        profileManager.createProfile(userId, name, email)
        verify (exactly = 1) { mockFirebaseDatabaseWrapper.writeNewUser(userId, name, email) }
    }

    @Test
    fun displayUserName() {
        val expectedUserName = "John Doe"
        val userId = "123456"
        val getNameOfUserMock: suspend (String, (String?) -> Unit) -> Unit = { _, callback ->
            callback(expectedUserName)
        }
        val userNameState = mutableStateOf("")
        runBlocking {
            getNameOfUserMock(userId) { userName ->
                userNameState.value = userName ?: "User not found"
            }
        }
        val actualUserName = userNameState.value
        assertEquals(expectedUserName, actualUserName)
    }

    @Test
    fun testDisplayUserPic() {
        val mockImageUrl = "https://example.com/profile-pic.jpg"
        var userPicState by mutableStateOf("")
        val onPicSelected: (String) -> Unit = { userPicState = it }
        onPicSelected(mockImageUrl)
        assertEquals(mockImageUrl, userPicState)
    }

    @Test
    fun testUserUploadList() {
        val mockEmail = "test@example.com"
        val mockInventories = listOf(
            Inventory(
                name = "Inventory 1",
                userId = "test@example.com",
                description = "Description 1",
                imageLinks = listOf("link1", "link2"),
                price = 10.0,
                subject = "Subject 1",
                category = "Category 1",
                condition = "Condition 1",
                timeStamp = "ts 1"
            ),
            Inventory(
                name = "Inventory 2",
                userId = "other@example.com",
                description = "Description 2",
                imageLinks = listOf("link3", "link4"),
                price = 20.0,
                subject = "Subject 2",
                category = "Category 2",
                condition = "Condition 2",
                timeStamp = "ts 2"
            )
        )
        val mockHousings = listOf(
            Housing(
                name = "Housing 1",
                description = "Description 1",
                imageLinks = listOf("link1", "link2"),
                userId = "test@example.com",
                price = 1000.0,
                startDate = "2024-03-01",
                endDate = "2024-03-31",
                address = "Address 1",
                propertyType = "Property Type 1",
                genderRestriction = "Gender Restriction 1",
                numOfGuests = 5,
                numOfBedrooms = 3,
                numOfBathrooms = 2,
                timeStamp = "ts 1"
            ),
            Housing(
                name = "Housing 2",
                description = "Description 2",
                imageLinks = listOf("link3", "link4"),
                userId = "other@example.com",
                price = 2000.0,
                startDate = "2024-04-01",
                endDate = "2024-04-30",
                address = "Address 2",
                propertyType = "Property Type 2",
                genderRestriction = "Gender Restriction 2",
                numOfGuests = 8,
                numOfBedrooms = 4,
                numOfBathrooms = 3,
                timeStamp = "ts 2"
            )
        )
        var selectedIndexInventory by mutableStateOf(-1)
        var selectedIndexHousing by mutableStateOf(-1)
        mockInventories.forEachIndexed { index, inventory ->
            if (inventory.userId == mockEmail) {
                selectedIndexInventory = index
            }
        }
        mockHousings.forEachIndexed { index, housing ->
            if (housing.userId == mockEmail) {
                selectedIndexHousing = index
            }
        }
        assertEquals(0, selectedIndexInventory)
        assertEquals(0, selectedIndexHousing)
    }
}