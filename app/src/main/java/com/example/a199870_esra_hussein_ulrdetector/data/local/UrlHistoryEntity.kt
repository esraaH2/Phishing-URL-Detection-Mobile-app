package com.example.a199870_esra_hussein_ulrdetector.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "url_history")
data class UrlHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val url: String,
    val result: String,
    val timestamp: Long = System.currentTimeMillis()
)
