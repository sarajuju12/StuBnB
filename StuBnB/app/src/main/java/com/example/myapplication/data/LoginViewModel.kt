package com.example.myapplication.data

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private var loginState = mutableStateOf(LoginState())

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EmailChange -> {
                loginState.value = loginState.value.copy(
                    email = event.email
                )
            }
            is LoginEvent.PasswordChange -> {
                loginState.value = loginState.value.copy(
                    password = event.password
                )
            }
            is LoginEvent.ButtonClicked -> {
                login()
            }
        }
    }

    private fun login() {
        Log.d(ContentValues.TAG, loginState.value.toString())
    }
}