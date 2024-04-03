package com.example.myapplication.navigatorTest

import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import org.junit.Assert
import org.junit.Test

class navigatorTest {
    val navigator = Navigator;
    @Test
    fun testNavigator() {

        // default screen
        Assert.assertEquals(navigator.currentScreen.value, Screen.Login)

        navigator.navigate(Screen.CreateAccount)
        Assert.assertEquals(navigator.currentScreen.value, Screen.CreateAccount)

        navigator.navigate(Screen.HomeInventory)
        Assert.assertEquals(navigator.currentScreen.value, Screen.HomeInventory)

        navigator.navigate(Screen.HomeWishlist)
        Assert.assertEquals(navigator.currentScreen.value, Screen.HomeWishlist)

        navigator.navigate(Screen.HomeInbox)
        Assert.assertEquals(navigator.currentScreen.value, Screen.HomeInbox)

        navigator.navigate(Screen.UploadInventory)
        Assert.assertEquals(navigator.currentScreen.value, Screen.UploadInventory)


    }
}