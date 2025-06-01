package com.example.screenshotorganizer.di

import android.content.Context
import androidx.room.Room
import com.example.screenshotorganizer.data.dao.CategoryDao
import com.example.screenshotorganizer.data.dao.ScreenshotDao
import com.example.screenshotorganizer.data.database.AppDatabase
import com.example.screenshotorganizer.repository.CategoryRepository
import com.example.screenshotorganizer.repository.ScreenshotRepository
import com.example.screenshotorganizer.service.ImageClassifierService
import com.example.screenshotorganizer.service.OCRService
import com.example.screenshotorganizer.service.TextSummarizerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "screenshot_organizer_db"
        ).build()
    }
    
    @Provides
    fun provideScreenshotDao(database: AppDatabase): ScreenshotDao {
        return database.screenshotDao()
    }
    
    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }
    
    @Provides
    @Singleton
    fun provideScreenshotRepository(screenshotDao: ScreenshotDao): ScreenshotRepository {
        return ScreenshotRepository(screenshotDao)
    }
    
    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepository(categoryDao)
    }
    
    @Provides
    @Singleton
    fun provideOCRService(@ApplicationContext context: Context): OCRService {
        return OCRService(context)
    }
    
    @Provides
    @Singleton
    fun provideImageClassifierService(@ApplicationContext context: Context): ImageClassifierService {
        return ImageClassifierService(context)
    }
    
    @Provides
    @Singleton
    fun provideTextSummarizerService(): TextSummarizerService {
        return TextSummarizerService()
    }
}
