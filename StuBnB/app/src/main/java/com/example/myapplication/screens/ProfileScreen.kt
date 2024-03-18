package com.example.myapplication.screens
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.myapplication.R
import com.example.myapplication.components.ActionButton
import com.example.myapplication.data.HomeViewModel
import com.example.myapplication.data.LoginViewModel
import com.example.myapplication.data.housing.HousingCallback
import com.example.myapplication.data.housing.HousingRepository
import com.example.myapplication.data.repositories.InventoryCallback
import com.example.myapplication.data.repositories.InventoryRepository
import com.example.myapplication.models.Housing
import com.example.myapplication.models.Inventory
import com.example.myapplication.models.User
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Purple40
import com.example.myapplication.ui.theme.poppins
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

var uploadProgress = mutableStateOf(false)

@Composable
fun DisplayProfileScreen(homeViewModel: HomeViewModel = viewModel(), loginViewModel: LoginViewModel = viewModel()) {

    val userId = loginViewModel.getEncryptedEmail()
    var showDialog by remember { mutableStateOf(false)}
    if (showDialog) {
        UploadListingPopup(onDismiss = {showDialog=false})
    }
    var selectedProfilePicUri by remember { mutableStateOf<Uri?>(null) }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize())
        {
            // LOG OUT button at top right
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, top = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { homeViewModel.logout() },
                            enabled = true, // Modify this as per your logic
                            colors = ButtonDefaults.buttonColors(Purple40), // Modify the color as needed
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text("LOG OUT")
                        }
                    }
                }
                item {
                    DisplayUserPic(userId = userId) { uri ->
                        selectedProfilePicUri = uri
                    }
                    DisplayUserName(userId = userId)
                    ActionButton(
                        value = "UPLOAD A LISTING",
                        buttonClicked = { showDialog = true },
                        isEnabled = true
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    Text(
                        "My Listings",
                        color = Color.Black,
                        fontSize = 30.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InventoryHousingScreen(email = userId)
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }

            if (uploadProgress.value) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun UploadListingPopup(
    onDismiss:()->Unit
) {

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { /*TODO*/ },
        modifier = Modifier.height(250.dp),

        text = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { Navigator.navigate(Screen.UploadHousing) },
                    modifier = Modifier
                        .width(180.dp)
                        .padding(10.dp)
                ) {
                    Text(text = "Housing Listing")
                }

                Button(
                    onClick = { Navigator.navigate(Screen.UploadInventory) },
                    modifier = Modifier
                        .width(180.dp)
                        .padding(10.dp)
                ) {
                    Text(text = "Inventory Listing")
                }
            }
        }
    )
}

@Composable
fun DisplayUserName(userId: String) {
    val userNameState = remember { mutableStateOf("") }
    getNameOfUser({ userName ->
        userNameState.value = userName ?: "User not found"
    }, userId)
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "${userNameState.value}",
            color = Color.Black,
            fontSize = 30.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun DisplayUserPic(userId: String, onPicSelected: (Uri) -> Unit) {
    val userPicState = remember { mutableStateOf("") }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            onPicSelected(it)
            uploadImageToStorage(it, userId, userPicState)
        }
    }
    getProfilePic({ profilePicUrl ->
        if (profilePicUrl != null) {
            userPicState.value = profilePicUrl
        }
    }, userId)
    val painter = rememberImagePainter(
        data = userPicState.value, // URL of the user's profile picture
        builder = {
            error(R.drawable.profile) // Default profile picture drawable
            transformations(CircleCropTransformation()) // Apply circular transformation
        }
    )
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(BorderStroke(1.dp, Color.Black), CircleShape)
                .clickable {
                    // Trigger the opening of the gallery
                    galleryLauncher.launch("image/*")
                },
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun UserUploadList(inventories: List<Inventory>, housings: List<Housing>, email: String) {

    var selectedIndexInventory by rememberSaveable { mutableStateOf(-1) }
    var selectedIndexHousing by rememberSaveable { mutableStateOf(-1) }

    Column {
        inventories.forEachIndexed { index, inventory ->
            if (inventory.userId == email) {
                val onItemClick = {
                    selectedIndexInventory = index
                }
                InventoryItem(inventory = inventory, onClick = onItemClick, delete = true)
            }
        }
        housings.forEachIndexed { index, housing ->
            if (housing.userId == email) {
                val onItemClick = {
                    selectedIndexHousing = index
                }
                HousingItem(housing = housing, onClick = onItemClick, delete = true)
            }
        }
    }

    if (selectedIndexInventory >= 0) {
        Navigator.navigate(Screen.Inventory(inventories[selectedIndexInventory])) // pass in the selected item
    }

    if (selectedIndexHousing >= 0) {
        Navigator.navigate(Screen.House(housings[selectedIndexHousing])) // navigator is an object
    }
}

@Composable
fun InventoryHousingScreen(email: String) {
    val tempInventoryRepository = InventoryRepository()
    val listOfInventory = remember { mutableStateOf<List<Inventory>>(emptyList()) }

    // Fetch inventory data when the screen is first displayed or recomposed
    LaunchedEffect(key1 = true) {
        tempInventoryRepository.getInventory(object : InventoryCallback {
            override fun onInventoryLoaded(inventoryList: MutableList<Inventory>) {
                // Update the state with the new inventory data
                listOfInventory.value = inventoryList
            }
        })
    }

    val tempHousingRepository = HousingRepository()
    val listOfHousing = remember { mutableStateOf<List<Housing>>(emptyList()) }

    // Fetch inventory data when the screen is first displayed or recomposed
    LaunchedEffect(key1 = true) {
        // does not contribute to UI
        tempHousingRepository.getHousing(object : HousingCallback {
            override fun onHousingLoaded(housingList: MutableList<Housing>) {
                // Update the state with the new inventory data
                listOfHousing.value = housingList
            }
        })
    }

    MyApplicationTheme {
        UserUploadList(listOfInventory.value, listOfHousing.value, email)
    }
}

private fun uploadImageToStorage(imageUri: Uri, userId: String, userPicState: MutableState<String>) {
    uploadProgress.value = true
    val storageReference = FirebaseStorage.getInstance().reference
    val fileReference = storageReference.child("${System.currentTimeMillis()}_${imageUri.lastPathSegment}")
    val uploadTask = fileReference.putFile(imageUri)
    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            throw task.exception ?: Exception("Unknown error")
        }
        fileReference.downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            fileReference.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                updateProfilePicture(imageUrl, userId, userPicState)
            }
        } else {
            task.exception ?: Exception("Unknown error")
        }
    }
}

private fun updateProfilePicture(imageUrl: String, userId: String, userPicState: MutableState<String>) {
    val databaseReference = FirebaseDatabase.getInstance().getReference("users/$userId/imageLink")
    databaseReference.setValue(imageUrl).addOnSuccessListener {
        getProfilePic({ profilePicUrl ->
            if (profilePicUrl != null) {
                userPicState.value = profilePicUrl
            }
        }, userId)
    }
}

fun getNameOfUser(callback: (String?) -> Unit, userEmail: String) {
    val database = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.getReference("users")

    myRef.child(userEmail).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(userSnapshot: DataSnapshot) {
            val user = userSnapshot.getValue(User::class.java)
            val userName = user?.name
            callback(userName)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e("getNameOfUser", "Failed to get name of user $userEmail.", databaseError.toException())
            callback(null)
        }
    })
}

fun getProfilePic(callback: (String?) -> Unit, userEmail: String) {
    val database = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.getReference("users")

    myRef.child(userEmail).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(userSnapshot: DataSnapshot) {
            val user = userSnapshot.getValue(User::class.java)
            val userPic = user?.imageLink
            callback(userPic)
            uploadProgress.value = false
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e("getProfilePic", "Failed to get image of user $userEmail.", databaseError.toException())
            callback(null)
        }
    })
}
