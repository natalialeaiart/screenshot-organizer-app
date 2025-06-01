package com.example.screenshotorganizer.repository

import com.example.screenshotorganizer.data.dao.ScreenshotDao
import com.example.screenshotorganizer.data.model.Screenshot
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScreenshotRepository @Inject constructor(
    private val screenshotDao: ScreenshotDao
) {
    fun getAllScreenshots(): Flow<List<Screenshot>> {
        return screenshotDao.getAllScreenshots()
    }
    
    fun getScreenshotsByCategory(categoryId: Long): Flow<List<Screenshot>> {
        return screenshotDao.getScreenshotsByCategory(categoryId)
    }
    
    fun searchScreenshots(query: String): Flow<List<Screenshot>> {
        return screenshotDao.searchScreenshots(query)
    }
    
    suspend fun insertScreenshot(screenshot: Screenshot): Long {
        return screenshotDao.insertScreenshot(screenshot)
    }
    
    suspend fun updateScreenshot(screenshot: Screenshot) {
        screenshotDao.updateScreenshot(screenshot)
    }
    
    suspend fun deleteScreenshot(screenshot: Screenshot) {
        screenshotDao.deleteScreenshot(screenshot)
    }
    
    suspend fun getScreenshotById(id: Long): Screenshot? {
        return screenshotDao.getScreenshotById(id)
    }
}
