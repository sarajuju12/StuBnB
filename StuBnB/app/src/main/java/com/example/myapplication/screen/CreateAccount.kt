package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.app.*

@Composable
fun CreateAccount() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize().background(Color.White).padding(30.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TitleText(value = "Create your account")
            TextField(labelValue = "Name", painterResource = painterResource(id = R.drawable.profile))
            TextField(labelValue = "Email", painterResource = painterResource(id = R.drawable.email))
            PasswordTextField(labelValue = "Password", painterResource = painterResource(id = R.drawable.lock))
            Spacer(modifier = Modifier.height(50.dp))
            ActionButton(value = "CREATE ACCOUNT")
            Spacer(modifier = Modifier.height(20.dp))
            LoginRedirect(onTextSelected = {})
        }
    }
}

@Preview
@Composable
fun PreviewSignUp() {
    CreateAccount()
}