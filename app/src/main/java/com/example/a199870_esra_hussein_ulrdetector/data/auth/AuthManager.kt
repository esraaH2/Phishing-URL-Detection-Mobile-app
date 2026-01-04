package com.example.a199870_esra_hussein_ulrdetector.data.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthManager {

    private val auth = FirebaseAuth.getInstance()

    suspend fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email,password).await()
    }
    fun currentUser()= auth.currentUser
    fun logout(){
        auth.signOut()
    }

    }