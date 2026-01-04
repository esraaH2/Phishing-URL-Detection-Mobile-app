package com.example.a199870_esra_hussein_ulrdetector.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UrlHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UrlHistoryEntity)

    @Query("SELECT * FROM url_history ORDER BY timestamp DESC")
    suspend fun getAll(): List<UrlHistoryEntity>

    @Delete
    suspend fun delete(item: UrlHistoryEntity)

    @Query("DELETE FROM url_history")
    suspend fun clearAll()
}
