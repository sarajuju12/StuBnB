package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.components.*
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen

@Composable
fun Login() {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(30.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo", modifier = Modifier.size(200.dp))
            }
            TitleText(value = "StuBnB")
            TextField(labelValue = "Email", painterResource = painterResource(id = R.drawable.email))
            PasswordTextField(labelValue = "Password", painterResource = painterResource(id = R.drawable.lock))
            Spacer(modifier = Modifier.height(50.dp))
            ActionButton(value = "SIGN IN")
            Spacer(modifier = Modifier.height(20.dp))
            LoginRedirect(login = false, onTextSelected = {
                Navigator.navigate(Screen.CreateAccount)
            })
        }
    }
}

@Preview
@Composable
fun PreviewLogin() {
    Login()
}