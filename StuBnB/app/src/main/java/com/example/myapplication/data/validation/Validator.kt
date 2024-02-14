package com.example.myapplication.data.validation

object Validator {
    private val EMAIL_REGEX = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+\$")
    fun validateName(name: String): ValidationResult {
        return ValidationResult((!name.isNullOrEmpty()))
    }
    fun validateEmail(email: String): ValidationResult {
        return ValidationResult((!email.isNullOrEmpty() && email.matches(EMAIL_REGEX)))
    }
    fun validatePassword(password: String): ValidationResult {
        return ValidationResult((!password.isNullOrEmpty() && password.length >= 6))
    }
}

data class ValidationResult (
    val status: Boolean = false
)