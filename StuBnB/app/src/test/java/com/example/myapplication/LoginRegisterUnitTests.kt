package com.example.myapplication

import com.example.myapplication.data.CreateAccountEvent
import com.example.myapplication.data.CreateAccountViewModel
import com.example.myapplication.data.LoginEvent
import com.example.myapplication.data.LoginViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.*
import org.junit.Test

open class FirebaseAuthWrapper {
    open fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
    }

    open fun registerWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
    }
}

class LoginManager(private val firebaseAuthWrapper: FirebaseAuthWrapper) {
    fun login(email: String, password: String): Task<AuthResult> {
        return firebaseAuthWrapper.signInWithEmailAndPassword(email, password)
    }
}

class RegistrationManager(private val firebaseAuthWrapper: FirebaseAuthWrapper) {
    fun register(email: String, password: String): Task<AuthResult> {
        return firebaseAuthWrapper.registerWithEmailAndPassword(email, password)
    }
}

class LoginViewModelTest {

    private val loginViewModel = LoginViewModel
    private val createAccountViewModel = CreateAccountViewModel()
    private val mockFirebaseAuthWrapper = mockk<FirebaseAuthWrapper>()
    private val loginManager = LoginManager(mockFirebaseAuthWrapper)
    private val registrationManager = RegistrationManager(mockFirebaseAuthWrapper)

    @Test
    fun loginValidateData() {
        // Test valid email and password
        loginViewModel.onEvent(LoginEvent.EmailChange("test@example.com"))
        loginViewModel.onEvent(LoginEvent.PasswordChange("123456"))
        assertTrue(loginViewModel.validationPassed.value)

        // Test invalid email and valid password
        loginViewModel.onEvent(LoginEvent.EmailChange("invalidemail"))
        loginViewModel.onEvent(LoginEvent.PasswordChange("123456"))
        assertFalse(loginViewModel.validationPassed.value)

        // Test valid email and invalid password
        loginViewModel.onEvent(LoginEvent.EmailChange("test@example.com"))
        loginViewModel.onEvent(LoginEvent.PasswordChange("123"))
        assertFalse(loginViewModel.validationPassed.value)

        // Test invalid email and invalid password
        loginViewModel.onEvent(LoginEvent.EmailChange("invalidemail"))
        loginViewModel.onEvent(LoginEvent.PasswordChange("123"))
        assertFalse(loginViewModel.validationPassed.value)
    }

    @Test
    fun registerValidateData() {
        // Test invalid email and valid password
        createAccountViewModel.onEvent(CreateAccountEvent.EmailChange("invalidemail"))
        createAccountViewModel.onEvent(CreateAccountEvent.PasswordChange("123456"))
        assertFalse(createAccountViewModel.validationPassed.value)

        // Test valid email and invalid password
        createAccountViewModel.onEvent(CreateAccountEvent.EmailChange("test@example.com"))
        createAccountViewModel.onEvent(CreateAccountEvent.PasswordChange("123"))
        assertFalse(createAccountViewModel.validationPassed.value)

        // Test invalid email and invalid password
        createAccountViewModel.onEvent(CreateAccountEvent.EmailChange("invalidemail"))
        createAccountViewModel.onEvent(CreateAccountEvent.PasswordChange("123"))
        assertFalse(createAccountViewModel.validationPassed.value)
    }

    @Test
    fun loginFail() {
        val email = "test@example.com"
        val password = "password"
        val mockedTask = mockk<Task<AuthResult>>()
        every { mockFirebaseAuthWrapper.signInWithEmailAndPassword(email, password) } returns mockedTask
        val result = loginManager.login(email, password)
        assertEquals(mockedTask, result)
    }

    @Test
    fun loginSuccess() {
        val email = "sarajuju12@gmail.com"
        val password = "123456"
        val mockedTask = mockk<Task<AuthResult>>()
        every { mockFirebaseAuthWrapper.signInWithEmailAndPassword(email, password) } returns mockedTask
        val result = loginManager.login(email, password)
        assertEquals(mockedTask, result)
    }

    @Test
    fun registrationFail() {
        // Duplicate user leads to failed registration
        val email = "sarajuju12@gmail.com"
        val password = "123456"
        val mockedTask = mockk<Task<AuthResult>>()
        every { mockFirebaseAuthWrapper.registerWithEmailAndPassword(email, password) } returns mockedTask
        val result = registrationManager.register(email, password)
        assertEquals(mockedTask, result)
    }
}