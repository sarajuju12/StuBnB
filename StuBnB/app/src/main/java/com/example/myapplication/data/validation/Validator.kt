package com.example.myapplication.data.validation

object Validator {
    fun validateName(name: String): ValidationResult {
        return ValidationResult((!name.isNullOrEmpty()))
    }
    fun validateEmail(email: String): ValidationResult {
        return ValidationResult((!email.isNullOrEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()))
    }
    fun validatePassword(password: String): ValidationResult {
        return ValidationResult((!password.isNullOrEmpty() && password.length >= 6))
    }
}

data class ValidationResult (
    val status: Boolean = false
)