package com.example.myapplication.data

import androidx.lifecycle.ViewModel
import com.example.myapplication.routers.Navigator
import com.example.myapplication.routers.Screen
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: ViewModel() {
    fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Navigator.navigate(Screen.Login)
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }
}