package com.example.myapplication.data

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.validation.Validator
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {
    var loginState = mutableStateOf(LoginState())
    var validationPassed = mutableStateOf(true)
    var loginProgress = mutableStateOf(false)
    var showAlert = mutableStateOf(false)
    var twoFactorAuth = mutableStateOf(false)

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EmailChange -> {
                loginState.value = loginState.value.copy(
                    email = event.email
                )
                validateData()
            }
            is LoginEvent.PasswordChange -> {
                loginState.value = loginState.value.copy(
                    password = event.password
                )
                validateData()
            }
            is LoginEvent.ButtonClicked -> {
                validateData()
                login()
            }
        }
    }

    private fun login() {
        loginProgress.value = true
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(loginState.value.email, loginState.value.password)
            .addOnCompleteListener {
                loginProgress.value = false
                if (it.isSuccessful) twoFactorAuth.value = true
                    // Navigator.navigate(Screen.Home)
            }
            .addOnFailureListener {
                loginProgress.value = false
                showAlert.value = true
            }
    }

    private fun validateData() {

        val emailResult = Validator.validateEmail(
            email = loginState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = loginState.value.password
        )

        Log.d(ContentValues.TAG, emailResult.toString())
        Log.d(ContentValues.TAG, passwordResult.toString())

        loginState.value = loginState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
        validationPassed.value = emailResult.status && passwordResult.status
    }
}