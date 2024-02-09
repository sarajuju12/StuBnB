package com.example.myapplication.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.components.*
import com.example.myapplication.data.CreateAccountViewModel
import com.example.myapplication.data.CreateAccountEvent
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen

@Composable
fun CreateAccount(createAccountViewModel: CreateAccountViewModel = viewModel()) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize().background(Color.White).padding(30.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TitleText(value = "Create your account")
            TextField(labelValue = "Name", painterResource = painterResource(id = R.drawable.profile),
                onTextSelected = {
                    createAccountViewModel.onEvent(CreateAccountEvent.NameChange(it))
                })
            TextField(labelValue = "Email", painterResource = painterResource(id = R.drawable.email),
                onTextSelected = {
                    createAccountViewModel.onEvent(CreateAccountEvent.EmailChange(it))
                })
            PasswordTextField(labelValue = "Password", painterResource = painterResource(id = R.drawable.lock),
                onTextSelected = {
                    createAccountViewModel.onEvent(CreateAccountEvent.PasswordChange(it))
                })
            Spacer(modifier = Modifier.height(50.dp))
            ActionButton(value = "CREATE ACCOUNT",
                buttonClicked = {
                    createAccountViewModel.onEvent(CreateAccountEvent.ButtonClicked)
                })
            Spacer(modifier = Modifier.height(20.dp))
            LoginRedirect(login = true, onTextSelected = {
                Navigator.navigate(Screen.Login)
            })
        }
    }
}

@Preview
@Composable
fun PreviewSignUp() {
    CreateAccount()
}