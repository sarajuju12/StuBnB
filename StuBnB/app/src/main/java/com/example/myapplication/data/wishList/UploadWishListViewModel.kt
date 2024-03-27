import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.LoginViewModel
import com.example.myapplication.data.wishList.UploadWishListState
import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


fun updateWishlistInventory(email: String, inventory: Inventory) {
    val safeEmail = email.replace(".", ",")

    val inventoryListRef = FirebaseDatabase.getInstance().getReference("wishlist")
        .child(safeEmail).child("inventory")

    val inventoryItemRef = inventoryListRef.child(inventory.name.trim())

    inventoryItemRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                // If the inventory item exists, delete it from the wishlist
                inventoryItemRef.removeValue()
            } else {
                inventoryItemRef.setValue(inventory)
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
        }
    })
}


fun updateWishlistHousing(email: String, house: Housing) {
    val safeEmail = email.replace(".", ",")

    val ref = FirebaseDatabase.getInstance().getReference("wishlist")
        .child(safeEmail).child("housing").child(house.name.trim())

    // Check if the house already exists in the wishlist
    ref.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                ref.removeValue()
            } else {
                ref.setValue(house)
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
        }
    })
}

class UploadWishListViewModel : ViewModel() {

    var uploadState = mutableStateOf(UploadWishListState()) // set to isHousing by default

    fun onEvent(event: UploadWishListEvent){
        when (event){
            is UploadWishListEvent.updateWishlistInventory -> {
                uploadState.value = uploadState.value.copy(
                    inventory = event.inventory
                )

                updateWishlistInventory(LoginViewModel.getEncryptedEmail(), uploadState.value.inventory)
            }

            is UploadWishListEvent.updateWishlistHousing -> {

                uploadState.value = uploadState.value.copy(
                    house = event.house
                )

                updateWishlistHousing(LoginViewModel.getEncryptedEmail(), uploadState.value.house)
            }

            else -> {

            }
        }

    }
}
