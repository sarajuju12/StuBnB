package com.example.myapplication.screens
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.myapplication.models.User
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.example.myapplication.ui.theme.poppins
import com.google.firebase.database.*

@Composable
fun DisplayProfileScreen(homeViewModel: HomeViewModel = viewModel(), loginViewModel: LoginViewModel = viewModel()) {

    val userId = loginViewModel.getEncryptedEmail()
    var showDialog by remember { mutableStateOf(false)}
    if (showDialog) {
        UploadListingPopup(onDismiss = {showDialog=false})
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DisplayUserPic(userId = userId)
                DisplayUserName(userId = userId)
                ActionButton(value = "UPLOAD A LISTING", buttonClicked = { showDialog = true }, isEnabled = true)
                Spacer(modifier = Modifier.height(50.dp))
                ActionButton(value = "LOG OUT", buttonClicked = { homeViewModel.logout() }, isEnabled = true)
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
fun DisplayUserPic(userId: String) {
    val userPicState = remember { mutableStateOf("") }
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
        val interactionSource = remember { MutableInteractionSource() }
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(BorderStroke(1.dp, Color.Black), CircleShape),
            contentScale = ContentScale.Crop
        )
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
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e("getProfilePic", "Failed to get image of user $userEmail.", databaseError.toException())
            callback(null)
        }
    })
}
