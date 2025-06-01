package com.example.screenshotorganizer.service

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Сервис для суммаризации текста с использованием Text2Summary
 */
class TextSummarizerService {
    
    /**
     * Создает суммаризацию текста с указанным коэффициентом сжатия
     * @param text Исходный текст для суммаризации
     * @param compressionRate Коэффициент сжатия (0.0-1.0), где 1.0 - без сжатия, 0.1 - сильное сжатие
     * @return Суммаризированный текст
     */
    suspend fun summarizeText(text: String, compressionRate: Float = 0.3f): String = withContext(Dispatchers.Default) {
        if (text.isBlank() || text.length < 100) {
            return@withContext text
        }
        
        try {
            // Используем библиотеку Text2Summary для суммаризации
            val summary = com.shubham0204.text2summary.Text2Summary.summarize(text, compressionRate)
            return@withContext summary
        } catch (e: Exception) {
            // В случае ошибки возвращаем исходный текст
            return@withContext text
        }
    }
    
    /**
     * Асинхронная версия суммаризации с колбэком
     */
    fun summarizeTextAsync(text: String, compressionRate: Float = 0.3f, callback: (String) -> Unit) {
        if (text.isBlank() || text.length < 100) {
            callback(text)
            return
        }
        
        try {
            val summaryCallback = object : com.shubham0204.text2summary.Text2Summary.SummaryCallback {
                override fun onSummaryProduced(summary: String) {
                    callback(summary)
                }
            }
            
            com.shubham0204.text2summary.Text2Summary.summarizeAsync(text, compressionRate, summaryCallback)
        } catch (e: Exception) {
            callback(text)
        }
    }
}
