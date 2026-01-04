package com.example.a199870_esra_hussein_ulrdetector

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UrlViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UrlViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UrlViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}