package com.example.myapplication.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.components.*
import com.example.myapplication.data.CreateAccountEvent
import com.example.myapplication.data.CreateAccountViewModel
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen

@Composable
fun CreateAccount(createAccountViewModel: CreateAccountViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize().background(Color.White).padding(30.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TitleText(value = "Create your account")
                TextField(labelValue = "Name", painterResource = painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        createAccountViewModel.onEvent(CreateAccountEvent.NameChange(it))
                    },
                    errorStatus = createAccountViewModel.createAccountState.value.nameError
                )
                TextField(labelValue = "Email", painterResource = painterResource(id = R.drawable.email),
                    onTextSelected = {
                        createAccountViewModel.onEvent(CreateAccountEvent.EmailChange(it))
                    },
                    errorStatus = createAccountViewModel.createAccountState.value.emailError
                )
                PasswordTextField(labelValue = "Password", painterResource = painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        createAccountViewModel.onEvent(CreateAccountEvent.PasswordChange(it))
                    },
                    errorStatus = createAccountViewModel.createAccountState.value.passwordError
                )
                Spacer(modifier = Modifier.height(50.dp))
                ActionButton(value = "CREATE ACCOUNT",
                    buttonClicked = {
                        createAccountViewModel.onEvent(CreateAccountEvent.ButtonClicked)
                    },
                    isEnabled = if (createAccountViewModel.createAccountState.value.name.isNullOrEmpty() and createAccountViewModel.createAccountState.value.email.isNullOrEmpty() and createAccountViewModel.createAccountState.value.password.isNullOrEmpty()) false else createAccountViewModel.validationPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))
                LoginRedirect(login = true, onTextSelected = {
                    Navigator.navigate(Screen.Login)
                })
            }
        }
        if (createAccountViewModel.createAccountProgress.value) {
            CircularProgressIndicator()
        }
    }
}