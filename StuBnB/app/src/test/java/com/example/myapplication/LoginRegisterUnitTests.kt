package com.example.myapplication

import com.example.myapplication.data.LoginEvent
import com.example.myapplication.data.LoginViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class LoginViewModelTest {

    private val loginViewModel = LoginViewModel()

    @Test
    fun validateData() {
        // Test valid email and password
        loginViewModel.onEvent(LoginEvent.EmailChange("test@example.com"))
        loginViewModel.onEvent(LoginEvent.PasswordChange("123456"))
        assertTrue(loginViewModel.validationPassed.value!!)

        // Test invalid email and valid password
        loginViewModel.onEvent(LoginEvent.EmailChange("invalidemail"))
        loginViewModel.onEvent(LoginEvent.PasswordChange("123456"))
        assertFalse(loginViewModel.validationPassed.value!!)

        // Test valid email and invalid password
        loginViewModel.onEvent(LoginEvent.EmailChange("test@example.com"))
        loginViewModel.onEvent(LoginEvent.PasswordChange("123"))
        assertFalse(loginViewModel.validationPassed.value!!)

        // Test invalid email and invalid password
        loginViewModel.onEvent(LoginEvent.EmailChange("invalidemail"))
        loginViewModel.onEvent(LoginEvent.PasswordChange("123"))
        assertFalse(loginViewModel.validationPassed.value!!)
    }
}