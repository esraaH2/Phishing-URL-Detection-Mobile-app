package com.example.a199870_esra_hussein_ulrdetector.data.remote

import com.example.a199870_esra_hussein_ulrdetector.data.local.UrlHistoryEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private fun requireUserId(): String =
        auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")

    private fun historyCollection() =
        db.collection("users")
            .document(requireUserId())
            .collection("history")

    suspend fun uploadHistoryItem(item: UrlHistoryEntity) {
        val data: Map<String, Any> = mapOf(
            "url" to item.url,
            "result" to item.result,
            "timestamp" to item.timestamp
        )

        historyCollection()
            .document(item.timestamp.toString())
            .set(data)
            .await()
    }

    suspend fun fetchHistory(): List<UrlHistoryEntity> {
        val snapshot = historyCollection()
            .orderBy("timestamp")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val url = doc.getString("url") ?: return@mapNotNull null
            val result = doc.getString("result") ?: return@mapNotNull null
            val timestamp = doc.getLong("timestamp") ?: return@mapNotNull null

            UrlHistoryEntity(
                url = url,
                result = result,
                timestamp = timestamp
            )
        }
    }

    suspend fun deleteHistoryItem(item: UrlHistoryEntity) {
        historyCollection()
            .document(item.timestamp.toString())
            .delete()
            .await()
    }

    suspend fun clearAllHistory() {
        val snapshot = historyCollection().get().await()
        for (doc in snapshot.documents) {
            doc.reference.delete().await()
        }
    }
}
