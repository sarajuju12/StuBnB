import com.example.myapplication.data.inventory.UploadInventoryEvent
import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory


sealed class UploadWishListEvent {
    data class updateWishlistInventory(val inventory: Inventory): UploadWishListEvent()
    data class updateWishlistHousing(val house: Housing): UploadWishListEvent()

}
