package com.example.myapplication.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.components.AlertDialogLogin
import com.example.myapplication.data.validation.Validator
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class CreateAccountViewModel: ViewModel() {
    var createAccountState = mutableStateOf(CreateAccountState())
    var validationPassed = mutableStateOf(true)
    var createAccountProgress = mutableStateOf(false)

    fun onEvent(event: CreateAccountEvent) {
        when(event) {
            is CreateAccountEvent.NameChange -> {
                createAccountState.value = createAccountState.value.copy(
                    name = event.name
                )
                validateData()
            }
            is CreateAccountEvent.EmailChange -> {
                createAccountState.value = createAccountState.value.copy(
                    email = event.email
                )
                validateData()
            }
            is CreateAccountEvent.PasswordChange -> {
                createAccountState.value = createAccountState.value.copy(
                    password = event.password
                )
                validateData()
            }
            is CreateAccountEvent.ButtonClicked -> {
                validateData()
                createAccount(email = createAccountState.value.email, password = createAccountState.value.password)
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        createAccountProgress.value = true
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                createAccountProgress.value = false
                if (it.isSuccessful) Navigator.navigate(Screen.Login)
            }
            .addOnFailureListener {
                createAccountProgress.value = false
            }
    }

    private fun validateData() {
        val nameResult = Validator.validateName(
            name = createAccountState.value.name
        )

        val emailResult = Validator.validateEmail(
            email = createAccountState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = createAccountState.value.password
        )

        Log.d(TAG, nameResult.toString())
        Log.d(TAG, emailResult.toString())
        Log.d(TAG, passwordResult.toString())

        createAccountState.value = createAccountState.value.copy(
            nameError = nameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
        validationPassed.value = nameResult.status && emailResult.status && passwordResult.status
    }
}