package com.example.a199870_esra_hussein_ulrdetector

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a199870_esra_hussein_ulrdetector.data.TfLiteModel
import com.example.a199870_esra_hussein_ulrdetector.data.local.DatabaseProvider
import com.example.a199870_esra_hussein_ulrdetector.data.local.UrlHistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import com.google.firebase.auth.FirebaseAuth
import com.example.a199870_esra_hussein_ulrdetector.data.remote.FirestoreRepository


data class FeatureExtractionResult(
    val features: FloatArray,
    val reasons: List<String>
)

class UrlViewModel(application: Application) : AndroidViewModel(application) {

    private val tfliteModel = TfLiteModel(application.applicationContext)
    private val firestoreRepo = FirestoreRepository()

    // ROOM DAO
    private val dao = DatabaseProvider.get(application).urlHistoryDao()

    // StateFlow history for Compose
    private val _history = MutableStateFlow<List<UrlHistoryEntity>>(emptyList())
    val history: StateFlow<List<UrlHistoryEntity>> = _history

    private var _lastScannedUrl: String = ""
    val lastScannedUrl: String get() = _lastScannedUrl

    private var _currentPrediction: String = "Not scanned yet"
    val currentPrediction: String get() = _currentPrediction

    private var _lastReasons: List<String> = emptyList()
    val lastReasons: List<String> get() = _lastReasons

    // get history
    fun loadHistory(){
        viewModelScope.launch(Dispatchers.IO) {
            _history.value = dao.getAll()
        }
    }

    // Save URL Result
    fun saveToHistory(url: String, result: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val entity = UrlHistoryEntity(
                url = url,
                result = result
            )

            dao.insert(entity)
            _history.value = dao.getAll()

            // Upload only if logged in
            if (FirebaseAuth.getInstance().currentUser != null) {
                try {
                    firestoreRepo.uploadHistoryItem(entity)
                } catch (_: Exception) {

                }
            }
        }
    }


    // Delete one item
    fun deleteHistoryItem(item: UrlHistoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(item)
            _history.value = dao.getAll()
        }
    }

    // Clear all
    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.clearAll()
            _history.value = emptyList()
        }
    }

    private fun extractFeaturesWithReasons(url: String): FeatureExtractionResult {
        val reasons = mutableListOf<String>()
        val features = FloatArray(15)

        return try {
            val parsed = URL(url)
            val domain = parsed.host
            val path = parsed.file

            features[0] = (minOf(url.length, 100) / 100.0f)
            if (url.length > 75) reasons.add("URL is unusually long")

            val dotCount = domain.count { it == '.' }
            features[1] = (minOf(dotCount, 5) / 5.0f)
            if (dotCount > 2) reasons.add("Contains excessive subdomains")

            features[2] = if ('-' in domain) 1.0f else 0.0f
            if ('-' in domain) reasons.add("Domain contains hyphens")

            val digits = url.count { it.isDigit() }
            features[3] = (minOf(digits, 10) / 10.0f)
            if (digits > 5) reasons.add("Contains an unusually high number of digits")

            features[4] = if ('@' in url) 1.0f else 0.0f
            if ('@' in url) reasons.add("Contains '@' symbol (common in phishing URLs)")

            features[5] = if ("//" in path) 1.0f else 0.0f
            if ("//" in path) reasons.add("Path contains suspicious '//' sequence")

            features[6] = (minOf(domain.length, 50) / 50.0f)
            if (domain.length > 30) reasons.add("Domain name is excessively long")

            val special = url.count { !it.isLetterOrDigit() && it !in ".-/:@" }
            features[7] = (minOf(special, 10) / 10.0f)
            if (special > 5) reasons.add("Contains many unusual special characters")

            features[8] = if (url.startsWith("http://")) 1.0f else 0.0f
            if (url.startsWith("http://")) reasons.add("Uses insecure HTTP (not HTTPS)")

            val suspiciousWords = listOf("verify", "confirm", "secure", "update", "login", "account", "paypal", "bank", "free")
            val hasSuspicious = suspiciousWords.any { url.lowercase().contains(it) }
            features[9] = if (hasSuspicious) 1.0f else 0.0f
            if (hasSuspicious) {
                val found = suspiciousWords.filter { url.lowercase().contains(it) }
                reasons.add("Contains suspicious keywords: ${found.joinToString(", ")}")
            }

            features[10] = (minOf(url.count { it == '/' }, 5) / 5.0f)
            if (url.count { it == '/' } > 3) reasons.add("Path structure is overly complex")

            features[11] = (minOf(url.count { it == '?' }, 3) / 3.0f)
            if (url.count { it == '?' } > 2) reasons.add("Contains many query parameters")

            features[12] = (minOf(url.count { it == '=' }, 5) / 5.0f)
            if (url.count { it == '=' } > 3) reasons.add("Contains many key-value assignments")

            features[13] = (minOf(url.count { it == ';' }, 3) / 3.0f)
            if (url.count { it == ';' } > 1) reasons.add("Contains semicolons (uncommon in safe URLs)")

            val parts = domain.split('.').size
            features[14] = parts.toFloat()
            if (parts > 3) reasons.add("Domain structure is abnormally complex")

            FeatureExtractionResult(features, reasons)
        } catch (e: Exception) {
            FeatureExtractionResult(FloatArray(15), listOf("Failed to parse URL"))
        }
    }

    fun predictPhishing(url: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val extraction = withContext(Dispatchers.IO) { extractFeaturesWithReasons(url) }
                val score = withContext(Dispatchers.IO) { tfliteModel.predict(extraction.features) }

                _currentPrediction = if (score > 0.5f) {
                    "PHISHING ❌ (${"%.3f".format(score)})"
                } else {
                    "SAFE ✅ (${"%.3f".format(score)})"
                }

                _lastReasons = extraction.reasons
                _lastScannedUrl = url

                onComplete(true)
            } catch (e: Exception) {
                _currentPrediction = "ERROR: ${e.message}"
                _lastReasons = listOf("Failed to analyze URL")
                onComplete(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        tfliteModel.close()
    }
}
