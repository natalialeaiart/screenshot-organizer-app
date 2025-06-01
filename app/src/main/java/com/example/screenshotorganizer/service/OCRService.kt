package com.example.screenshotorganizer.service

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Сервис для распознавания текста на изображениях с использованием ML Kit
 */
class OCRService(private val context: Context) {
    
    private val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    
    /**
     * Извлекает текст из изображения по URI
     */
    suspend fun extractTextFromImage(imageUri: Uri): String = withContext(Dispatchers.IO) {
        try {
            val image = InputImage.fromFilePath(context, imageUri)
            return@withContext recognizeText(image)
        } catch (e: IOException) {
            throw IOException("Не удалось обработать изображение", e)
        }
    }
    
    /**
     * Извлекает текст из Bitmap
     */
    suspend fun extractTextFromBitmap(bitmap: Bitmap): String = withContext(Dispatchers.IO) {
        val image = InputImage.fromBitmap(bitmap, 0)
        return@withContext recognizeText(image)
    }
    
    /**
     * Выполняет распознавание текста с помощью ML Kit
     */
    private suspend fun recognizeText(image: InputImage): String = suspendCancellableCoroutine { continuation ->
        textRecognizer.process(image)
            .addOnSuccessListener { visionText ->
                val extractedText = visionText.text
                continuation.resume(extractedText)
            }
            .addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }
    }
}
