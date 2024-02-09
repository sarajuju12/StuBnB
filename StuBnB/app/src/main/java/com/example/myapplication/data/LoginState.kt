package com.example.myapplication.data

data class LoginState(
    var email: String = "",
    var password: String = "",
    var emailError: Boolean = true,
    var passwordError: Boolean = true
)
