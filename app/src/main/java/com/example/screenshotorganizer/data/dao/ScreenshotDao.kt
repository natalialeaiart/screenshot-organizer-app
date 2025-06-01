package com.example.screenshotorganizer.data.dao

import androidx.room.*
import com.example.screenshotorganizer.data.model.Screenshot
import kotlinx.coroutines.flow.Flow

@Dao
interface ScreenshotDao {
    @Query("SELECT * FROM screenshots ORDER BY timestamp DESC")
    fun getAllScreenshots(): Flow<List<Screenshot>>
    
    @Query("SELECT * FROM screenshots WHERE categoryId = :categoryId ORDER BY timestamp DESC")
    fun getScreenshotsByCategory(categoryId: Long): Flow<List<Screenshot>>
    
    @Query("SELECT * FROM screenshots WHERE extractedText LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchScreenshots(query: String): Flow<List<Screenshot>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScreenshot(screenshot: Screenshot): Long
    
    @Update
    suspend fun updateScreenshot(screenshot: Screenshot)
    
    @Delete
    suspend fun deleteScreenshot(screenshot: Screenshot)
    
    @Query("SELECT * FROM screenshots WHERE id = :id")
    suspend fun getScreenshotById(id: Long): Screenshot?
}
