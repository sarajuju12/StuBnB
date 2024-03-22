package com.example.myapplication.views


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.components.*
import com.example.myapplication.data.LoginViewModel
import com.example.myapplication.data.inventory.UploadInventoryEvent
import com.example.myapplication.data.inventory.UploadInventoryViewModel
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen

@Composable
fun UploadInventory(loginViewModel: LoginViewModel = viewModel(), uploadInventoryViewModel: UploadInventoryViewModel = viewModel()) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val userId = loginViewModel.getEncryptedEmail()
    uploadInventoryViewModel.setEmail(userId)
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()) { uris ->
        val stringUris = uris.map { it.toString() }
        uploadInventoryViewModel.updateImageLinks(newImageLinks = stringUris.toMutableList())
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(30.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    TitleText(value = "Add Your Inventory Info")
                    if (uploadInventoryViewModel.uploadState.value.imageLinks.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier
                                .align(Center)
                                .height(screenHeight * 0.25f)
                        ) {
                            itemsIndexed(uploadInventoryViewModel.uploadState.value.imageLinks) { index: Int, item: String ->
                                ImagePreview(
                                    uri = Uri.parse(item),
                                    height = screenHeight * 0.25f,
                                    width = screenWidth * 0.5f,
                                    onClick = {
                                        uploadInventoryViewModel.onImageRemove(index)
                                    }
                                )
                            }
                        }
                    }
                    Button(
                        onClick = {
                            galleryLauncher.launch("image/*")
                        },
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        Text(text = "UPLOAD IMAGES")
                    }
                    TextField(
                        labelValue = "Item Name", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadInventoryViewModel.onEvent(UploadInventoryEvent.NameChange(it))
                        },
                        errorStatus = uploadInventoryViewModel.uploadState.value.nameError
                    )
                    TextField(
                        labelValue = "Description", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadInventoryViewModel.onEvent(UploadInventoryEvent.DescriptionChange(it))
                        },
                        errorStatus = uploadInventoryViewModel.uploadState.value.descriptionError
                    )
                    TextField(
                        labelValue = "Price", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadInventoryViewModel.onEvent(UploadInventoryEvent.PriceChange(it))
                        },
                        errorStatus = uploadInventoryViewModel.uploadState.value.priceError
                    )
                    TextField(
                        labelValue = "Subject", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadInventoryViewModel.onEvent(UploadInventoryEvent.SubjectChange(it))
                        },
                        errorStatus = uploadInventoryViewModel.uploadState.value.subjectError
                    )
                    TextField(
                        labelValue = "Category", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadInventoryViewModel.onEvent(UploadInventoryEvent.CategoryChange(it))
                        },
                        errorStatus = uploadInventoryViewModel.uploadState.value.categoryError
                    )
                    TextField(
                        labelValue = "Condition", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadInventoryViewModel.onEvent(UploadInventoryEvent.ConditionChange(it))
                        },
                        errorStatus = uploadInventoryViewModel.uploadState.value.conditionError
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    ActionButton(
                        value = "UPLOAD INVENTORY LISTING",
                        buttonClicked = {
                            uploadInventoryViewModel.onEvent(UploadInventoryEvent.ButtonClicked)
                        },
                        isEnabled = if (uploadInventoryViewModel.uploadState.value.name.isNullOrEmpty() and uploadInventoryViewModel.uploadState.value.description.isNullOrEmpty() and
                            uploadInventoryViewModel.uploadState.value.price.isNullOrEmpty() and uploadInventoryViewModel.uploadState.value.subject.isNullOrEmpty() and
                            uploadInventoryViewModel.uploadState.value.category.isNullOrEmpty() and uploadInventoryViewModel.uploadState.value.condition.isNullOrEmpty()
                        ) false else uploadInventoryViewModel.validationPassed.value
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            Navigator.navigate(Screen.HomeProfile)
                        }
                    ) {
                        Text(text = "BACK")
                    }
                    Spacer(modifier = Modifier.height(110.dp))
                }
            }
        }
        if (uploadInventoryViewModel.uploadProgress.value) {
            CircularProgressIndicator()
        }
        if (uploadInventoryViewModel.showAlert.value) {
            AlertDialogLogin(
                onDismissRequest = { uploadInventoryViewModel.showAlert.value = false },
                dialogTitle = "Error",
                dialogText = "Image upload is required."
            )
        }
    }
}