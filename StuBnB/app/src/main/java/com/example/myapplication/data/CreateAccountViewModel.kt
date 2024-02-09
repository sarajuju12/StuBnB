package com.example.myapplication.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CreateAccountViewModel: ViewModel() {
    private var createAccountState = mutableStateOf(CreateAccountState())

    fun onEvent(event: CreateAccountEvent) {
        when(event) {
            is CreateAccountEvent.NameChange -> {
                createAccountState.value = createAccountState.value.copy(
                    name = event.name
                )
            }
            is CreateAccountEvent.EmailChange -> {
                createAccountState.value = createAccountState.value.copy(
                    email = event.email
                )
            }
            is CreateAccountEvent.PasswordChange -> {
                createAccountState.value = createAccountState.value.copy(
                    password = event.password
                )
            }
        }
    }
}