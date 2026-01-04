package com.example.a199870_esra_hussein_ulrdetector.data

import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class TfLiteModel(private val context: Context) {

    private var interpreter: Interpreter? = null

    init {
        try {
            val modelBuffer = loadModelFile()
            interpreter = Interpreter(modelBuffer)
            Log.d("TfLiteModel", "✅ Model loaded successfully!")
        } catch (e: Exception) {
            Log.e("TfLiteModel", "❌ Failed to load model", e)
        }
    }

    private fun loadModelFile(): MappedByteBuffer {
        val assetManager = context.assets
        val fileDescriptor = assetManager.openFd("phishing_light.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun predict(input: FloatArray): Float {
        val model = interpreter ?: return -1f

        if (input.size != 15) {
            Log.e("TfLiteModel", "❌ Input size must be 15, got ${input.size}")
            return -1f
        }

        val inputBatch = arrayOf(input)
        val output = Array(1) { FloatArray(1) }

        try {
            model.run(inputBatch, output)
            return output[0][0]
        } catch (e: Exception) {
            Log.e("TfLiteModel", "❌ Prediction failed", e)
            return -1f
        }
    }

    fun close() {
        interpreter?.close()
    }
}