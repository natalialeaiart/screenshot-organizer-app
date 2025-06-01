package com.example.screenshotorganizer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.screenshotorganizer.data.model.Screenshot
import com.example.screenshotorganizer.repository.ScreenshotRepository
import com.example.screenshotorganizer.service.OCRService
import com.example.screenshotorganizer.service.ImageClassifierService
import com.example.screenshotorganizer.service.TextSummarizerService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val screenshotRepository: ScreenshotRepository,
    private val ocrService: OCRService,
    private val imageClassifierService: ImageClassifierService,
    private val textSummarizerService: TextSummarizerService
) : ViewModel() {

    private val _screenshots = MutableStateFlow<List<Screenshot>>(emptyList())
    val screenshots: StateFlow<List<Screenshot>> = _screenshots
    
    private val _searchResults = MutableStateFlow<List<Screenshot>>(emptyList())
    val searchResults: StateFlow<List<Screenshot>> = _searchResults
    
    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadScreenshots()
    }
    
    fun loadScreenshots() {
        viewModelScope.launch {
            screenshotRepository.getAllScreenshots()
                .catch { e ->
                    _errorMessage.value = "Ошибка загрузки скриншотов: ${e.message}"
                }
                .collect { list ->
                    _screenshots.value = list
                }
        }
    }
    
    fun searchScreenshots(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                _searchResults.value = emptyList()
                return@launch
            }
            
            screenshotRepository.searchScreenshots(query)
                .catch { e ->
                    _errorMessage.value = "Ошибка поиска: ${e.message}"
                }
                .collect { results ->
                    _searchResults.value = results
                }
        }
    }
    
    fun processScreenshot(path: String, bitmap: android.graphics.Bitmap) {
        viewModelScope.launch {
            _isProcessing.value = true
            try {
                // Извлечение текста с помощью OCR
                val extractedText = ocrService.extractTextFromBitmap(bitmap)
                
                // Суммаризация текста
                val summary = if (extractedText.isNotBlank()) {
                    textSummarizerService.summarizeText(extractedText)
                } else {
                    ""
                }
                
                // Классификация изображения
                val category = imageClassifierService.classifyImage(bitmap)
                
                // Сохранение в базу данных
                val screenshot = Screenshot(
                    path = path,
                    timestamp = Date(),
                    extractedText = extractedText,
                    summary = summary,
                    isProcessed = true
                    // categoryId будет добавлен позже после определения категории
                )
                
                screenshotRepository.insertScreenshot(screenshot)
                
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка обработки скриншота: ${e.message}"
            } finally {
                _isProcessing.value = false
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        imageClassifierService.close()
    }
}
