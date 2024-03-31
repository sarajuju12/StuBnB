import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class screenAndNavigatorTest {

    @Test
    fun screen() {
        assertTrue(Screen.CreateAccount is Screen)
        assertTrue(Screen.Login is Screen)
        assertTrue(Screen.HomeHousing is Screen)

        val housingItem = Housing()
        val inventoryItem = Inventory()

        assertTrue(Screen.House(housingItem, 0) is Screen)
        assertTrue(Screen.Inventory(inventoryItem, 1) is Screen)
    }

    // navigator test
    @Before
    fun setUp() {
        Navigator.navigate(Screen.Login)
    }

    @Test
    fun navigate_updatesCurrentScreen() {
        Navigator.navigate(Screen.CreateAccount)
        assertEquals(Screen.CreateAccount, Navigator.currentScreen.value)

        Navigator.navigate(Screen.HomeHousing)
        assertEquals(Screen.HomeHousing, Navigator.currentScreen.value)
    }
}
