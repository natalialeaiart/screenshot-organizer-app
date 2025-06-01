package com.example.screenshotorganizer.service

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.google.mediapipe.tasks.vision.imageclassifier.ImageClassifier
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.imageclassifier.ImageClassifierResult
import com.google.mediapipe.framework.image.BitmapImageBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Сервис для классификации изображений с использованием MediaPipe
 */
class ImageClassifierService(private val context: Context) {
    
    private var imageClassifier: ImageClassifier? = null
    
    /**
     * Инициализация классификатора
     */
    suspend fun initialize() = withContext(Dispatchers.IO) {
        try {
            val modelPath = "screenshot_classifier.tflite"
            
            val options = ImageClassifier.ImageClassifierOptions.builder()
                .setBaseOptions(
                    ImageClassifier.BaseOptions.builder()
                        .setModelAssetPath(modelPath)
                        .build()
                )
                .setRunningMode(RunningMode.IMAGE)
                .setMaxResults(5)
                .build()
                
            imageClassifier = ImageClassifier.createFromOptions(context, options)
        } catch (e: Exception) {
            throw IOException("Не удалось инициализировать классификатор", e)
        }
    }
    
    /**
     * Классифицирует изображение и возвращает наиболее вероятную категорию
     */
    suspend fun classifyImage(bitmap: Bitmap): String = withContext(Dispatchers.IO) {
        if (imageClassifier == null) {
            initialize()
        }
        
        val mpImage = BitmapImageBuilder(bitmap).build()
        val result = imageClassifier?.classify(mpImage)
        
        return@withContext extractTopCategory(result)
    }
    
    /**
     * Извлекает наиболее вероятную категорию из результатов классификации
     */
    private fun extractTopCategory(result: ImageClassifierResult?): String {
        if (result == null || result.classificationResult().classifications().isEmpty() || 
            result.classificationResult().classifications()[0].categories().isEmpty()) {
            return "Другое"
        }
        
        val topCategory = result.classificationResult().classifications()[0].categories()[0]
        return if (topCategory.score() > 0.5) {
            topCategory.categoryName()
        } else {
            "Другое"
        }
    }
    
    /**
     * Освобождает ресурсы классификатора
     */
    fun close() {
        imageClassifier?.close()
        imageClassifier = null
    }
}
