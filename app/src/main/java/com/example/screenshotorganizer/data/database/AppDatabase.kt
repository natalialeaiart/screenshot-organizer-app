package com.example.screenshotorganizer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.screenshotorganizer.data.dao.CategoryDao
import com.example.screenshotorganizer.data.dao.ScreenshotDao
import com.example.screenshotorganizer.data.model.Category
import com.example.screenshotorganizer.data.model.Screenshot
import com.example.screenshotorganizer.util.DateConverter

@Database(
    entities = [Screenshot::class, Category::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun screenshotDao(): ScreenshotDao
    abstract fun categoryDao(): CategoryDao
}
