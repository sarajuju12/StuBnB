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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.components.*
import com.example.myapplication.data.LoginViewModel
import com.example.myapplication.data.housing.UploadHousingEvent
import com.example.myapplication.data.housing.UploadHousingViewModel
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun UploadHousing(loginViewModel: LoginViewModel = viewModel(), uploadHousingViewModel: UploadHousingViewModel = viewModel()) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val userId = loginViewModel.getEncryptedEmail()
    uploadHousingViewModel.setEmail(userId)
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()) { uris ->
        val stringUris = uris.map { it.toString() }
        uploadHousingViewModel.updateImageLinks(newImageLinks = stringUris.toMutableList())
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(30.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    TitleText(value = "Add Your Housing Info")
                    if (uploadHousingViewModel.uploadState.value.imageLinks.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .height(screenHeight * 0.25f)
                        ) {
                            itemsIndexed(uploadHousingViewModel.uploadState.value.imageLinks) { index: Int, item: String ->
                                ImagePreview(
                                    uri = Uri.parse(item),
                                    height = screenHeight * 0.25f,
                                    width = screenWidth * 0.5f,
                                    onClick = {
                                        uploadHousingViewModel.onImageRemove(index)
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
                        labelValue = "University", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadHousingViewModel.onEvent(UploadHousingEvent.NameChange(it))
                        },
                        errorStatus = uploadHousingViewModel.uploadState.value.nameError
                    )
                    TextField(
                        labelValue = "Address", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadHousingViewModel.onEvent(UploadHousingEvent.AddressChange(it))
                        },
                        errorStatus = uploadHousingViewModel.uploadState.value.addressError
                    )
                    TextField(
                        labelValue = "Property Type", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadHousingViewModel.onEvent(UploadHousingEvent.PropertyTypeChange(it))
                        },
                        errorStatus = uploadHousingViewModel.uploadState.value.propertyTypeError
                    )
                    TextField(
                        labelValue = "Description", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadHousingViewModel.onEvent(UploadHousingEvent.DescriptionChange(it))
                        },
                        errorStatus = uploadHousingViewModel.uploadState.value.descriptionError
                    )
                    TextField(
                        labelValue = "Price (per month)", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadHousingViewModel.onEvent(UploadHousingEvent.PriceChange(it))
                        },
                        errorStatus = uploadHousingViewModel.uploadState.value.priceError
                    )
                    TextField(
                        labelValue = "Start Date (DD/MM/YYYY)", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadHousingViewModel.onEvent(UploadHousingEvent.StartDateChange(it))
                        },
                        errorStatus = uploadHousingViewModel.uploadState.value.startDateError
                    )
                    TextField(
                        labelValue = "End Date (DD/MM/YYYY)", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadHousingViewModel.onEvent(UploadHousingEvent.EndDateChange(it))
                        },
                        errorStatus = uploadHousingViewModel.uploadState.value.endDateError
                    )
                    TextField(
                        labelValue = "Gender Restriction", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadHousingViewModel.onEvent(UploadHousingEvent.GenderRestrictionChange(it))
                        },
                        errorStatus = uploadHousingViewModel.uploadState.value.genderRestrictionError
                    )
                    TextField(
                        labelValue = "Number of Guests", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadHousingViewModel.onEvent(UploadHousingEvent.NumOfGuestsChange(it))
                        },
                        errorStatus = uploadHousingViewModel.uploadState.value.numOfGuestsError
                    )
                    TextField(
                        labelValue = "Number of Bedrooms", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadHousingViewModel.onEvent(UploadHousingEvent.NumOfBedroomsChange(it))
                        },
                        errorStatus = uploadHousingViewModel.uploadState.value.numOfBedroomsError
                    )
                    TextField(
                        labelValue = "Number of Bathrooms", painterResource = painterResource(id = R.drawable.profile),
                        onTextSelected = {
                            uploadHousingViewModel.onEvent(UploadHousingEvent.NumOfBathroomsChange(it))
                        },
                        errorStatus = uploadHousingViewModel.uploadState.value.numOfBathroomsError
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ActionButton(
                        value = "UPLOAD HOUSING LISTING",
                        buttonClicked = {
                            uploadHousingViewModel.onEvent(UploadHousingEvent.ButtonClicked)
                        },
                        isEnabled = if (uploadHousingViewModel.uploadState.value.name.isNullOrEmpty() and uploadHousingViewModel.uploadState.value.description.isNullOrEmpty() and
                            uploadHousingViewModel.uploadState.value.price.isNullOrEmpty() and uploadHousingViewModel.uploadState.value.startDate.isNullOrEmpty() and
                            uploadHousingViewModel.uploadState.value.endDate.isNullOrEmpty() and uploadHousingViewModel.uploadState.value.address.isNullOrEmpty() and
                            uploadHousingViewModel.uploadState.value.propertyType.isNullOrEmpty() and uploadHousingViewModel.uploadState.value.genderRestriction.isNullOrEmpty() and
                            uploadHousingViewModel.uploadState.value.numOfGuests.isNullOrEmpty() and uploadHousingViewModel.uploadState.value.numOfBedrooms.isNullOrEmpty() and
                            uploadHousingViewModel.uploadState.value.numOfBathrooms.isNullOrEmpty()
                        ) false else uploadHousingViewModel.validationPassed.value
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
        if (uploadHousingViewModel.uploadProgress.value) {
            CircularProgressIndicator()
        }
        if (uploadHousingViewModel.showAlert.value) {
            AlertDialogLogin(
                onDismissRequest = { uploadHousingViewModel.showAlert.value = false },
                dialogTitle = "Error",
                dialogText = "Image upload is required."
            )
        }
    }
}
