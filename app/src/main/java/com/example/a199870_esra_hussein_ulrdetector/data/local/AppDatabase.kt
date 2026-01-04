package com.example.a199870_esra_hussein_ulrdetector.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UrlHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun urlHistoryDao(): UrlHistoryDao
}
