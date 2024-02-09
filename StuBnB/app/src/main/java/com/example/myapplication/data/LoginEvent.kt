package com.example.myapplication.data

sealed class LoginEvent {
    data class EmailChange(val email: String): LoginEvent()
    data class PasswordChange(val password: String): LoginEvent()
    object ButtonClicked: LoginEvent()
}