package com.example.screenshotorganizer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.util.Date

@Entity(
    tableName = "screenshots",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Screenshot(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val path: String,
    val timestamp: Date,
    val categoryId: Long? = null,
    val extractedText: String? = null,
    val summary: String? = null,
    val thumbnailPath: String? = null,
    val isProcessed: Boolean = false
)
