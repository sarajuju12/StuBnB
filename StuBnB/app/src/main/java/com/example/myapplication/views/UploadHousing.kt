package com.example.myapplication.views


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.components.ActionButton
import com.example.myapplication.components.TextField
import com.example.myapplication.components.TitleText
import com.example.myapplication.data.housing.*
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen

@Composable
fun UploadHousing(/* */) {

    Column(modifier = Modifier.fillMaxSize()) {
        ActionButton(value = "back",
            buttonClicked = { Navigator.navigate(Screen.Home) },
            isEnabled = true)
    }



//    var userId = loginViewModel.getEncryptedEmail()
//    var imageLinks = mutableListOf("picture.png") //placeholder
//    uploadInventoryViewModel.setEmailAndImage(userId, imageLinks)
//
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Surface(
//            color = Color.White,
//            modifier = Modifier.fillMaxSize().background(Color.White).padding(30.dp)
//        ) {
//            Column(modifier = Modifier.fillMaxSize()) {
//                TitleText(value = "Add Your Inventory Info")
//                TextField(labelValue = "Item Name", painterResource = painterResource(id = R.drawable.profile),
//                    onTextSelected = {
//                        uploadInventoryViewModel.onEvent(UploadInventoryEvent.NameChange(it))
//                    },
//                    errorStatus = uploadInventoryViewModel.uploadState.value.nameError
//                )
//                TextField(labelValue = "Description", painterResource = painterResource(id = R.drawable.profile),
//                    onTextSelected = {
//                        uploadInventoryViewModel.onEvent(UploadInventoryEvent.DescriptionChange(it))
//                    },
//                    errorStatus = uploadInventoryViewModel.uploadState.value.descriptionError
//                )
//                /*TextField(labelValue = "Item Name", painterResource = painterResource(id = R.drawable.profile),
//                    onTextSelected = {
//                        imageLinks = it
//                    }
//                )*/
//                TextField(labelValue = "Price", painterResource = painterResource(id = R.drawable.profile),
//                    onTextSelected = {
//                        uploadInventoryViewModel.onEvent(UploadInventoryEvent.PriceChange(it))
//                    },
//                    errorStatus = uploadInventoryViewModel.uploadState.value.priceError
//                )
//                TextField(labelValue = "Subject", painterResource = painterResource(id = R.drawable.profile),
//                    onTextSelected = {
//                        uploadInventoryViewModel.onEvent(UploadInventoryEvent.SubjectChange(it))
//                    },
//                    errorStatus = uploadInventoryViewModel.uploadState.value.subjectError
//                )
//                TextField(labelValue = "Category", painterResource = painterResource(id = R.drawable.profile),
//                    onTextSelected = {
//                        uploadInventoryViewModel.onEvent(UploadInventoryEvent.CategoryChange(it))
//                    },
//                    errorStatus = uploadInventoryViewModel.uploadState.value.categoryError
//                )
//                TextField(labelValue = "Condition", painterResource = painterResource(id = R.drawable.profile),
//                    onTextSelected = {
//                        uploadInventoryViewModel.onEvent(UploadInventoryEvent.ConditionChange(it))
//                    },
//                    errorStatus = uploadInventoryViewModel.uploadState.value.conditionError
//                )
//
//                Spacer(modifier = Modifier.height(50.dp))
//
//                ActionButton(
//                    value = "UPLOAD INVENTORY LISTING",
//                    buttonClicked = {
//                        uploadInventoryViewModel.onEvent(UploadInventoryEvent.ButtonClicked)
//                    },
//                    isEnabled = if (uploadInventoryViewModel.uploadState.value.name.isNullOrEmpty() and uploadInventoryViewModel.uploadState.value.description.isNullOrEmpty() and
//                        uploadInventoryViewModel.uploadState.value.price.isNullOrEmpty() and uploadInventoryViewModel.uploadState.value.subject.isNullOrEmpty() and
//                        uploadInventoryViewModel.uploadState.value.category.isNullOrEmpty() and uploadInventoryViewModel.uploadState.value.condition.isNullOrEmpty()) false else uploadInventoryViewModel.validationPassed.value
//                )
//                Spacer(modifier = Modifier.height(20.dp))
//                Button(
//                    onClick = {
//                        Navigator.navigate(Screen.Profile)
//                    }
//                ) {
//                    Text(text = "BACK")
//                }
//                Spacer(modifier = Modifier.height(20.dp))
//
//            }
//        }
//    }
}
