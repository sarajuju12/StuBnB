package com.example.myapplication.views

import androidx.compose.foundation.Image
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
import com.example.myapplication.data.LoginEvent
import com.example.myapplication.data.LoginState
import com.example.myapplication.data.LoginViewModel
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen

@Composable
fun Login(loginViewModel: LoginViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(30.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "logo",
                        modifier = Modifier.size(200.dp)
                    )
                }
                TitleText(value = "StuBnB")
                TextField(
                    labelValue = "Email", painterResource = painterResource(id = R.drawable.email),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginEvent.EmailChange(it))
                    },
                    errorStatus = loginViewModel.loginState.value.emailError
                )
                PasswordTextField(
                    labelValue = "Password", painterResource = painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginEvent.PasswordChange(it))
                    },
                    errorStatus = loginViewModel.loginState.value.passwordError
                )
                Spacer(modifier = Modifier.height(50.dp))
                ActionButton(
                    value = "SIGN IN",
                    buttonClicked = {
                        loginViewModel.onEvent(LoginEvent.ButtonClicked)
                    },
                    isEnabled = if (loginViewModel.loginState.value.email.isNullOrEmpty() and loginViewModel.loginState.value.password.isNullOrEmpty()) false else loginViewModel.validationPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))
                LoginRedirect(login = false, onTextSelected = {
                    loginViewModel.loginState.value = LoginState()
                    Navigator.navigate(Screen.CreateAccount)
                })
            }
        }
        if (loginViewModel.loginProgress.value) {
            CircularProgressIndicator()
        }
        if (loginViewModel.showAlert.value) {
            AlertDialogLogin(
                onDismissRequest = { loginViewModel.showAlert.value = false },
                dialogTitle = "Error",
                dialogText = "The email or password you entered is incorrect."
            )
        }
        if (loginViewModel.twoFactorAuth.value) {
            TwoFactorAuthentication(
                onDismissRequest = { loginViewModel.twoFactorAuth.value = false },
                onConfirmation = {
                    loginViewModel.twoFactorAuth.value = false
                    Navigator.navigate(Screen.HomeHousing)
                },
                dialogTitle = "Two-Factor Authentication",
                dialogText = "Are you logging in to StuBnB?"
            )
        }
    }
}