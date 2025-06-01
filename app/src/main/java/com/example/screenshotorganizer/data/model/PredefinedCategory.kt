package com.example.screenshotorganizer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Предустановленные категории для скриншотов
 */
@Entity(tableName = "predefined_categories")
data class PredefinedCategory(
    @PrimaryKey
    val id: Int,
    val name: String,
    val iconResourceId: Int,
    val color: Int
)

/**
 * Класс для инициализации предустановленных категорий
 */
object PredefinedCategories {
    // Цвета для категорий
    private val COLORS = listOf(
        0xFFD2B48C.toInt(), // Бежевый
        0xFFA67B5B.toInt(), // Коричневый
        0xFFFFB6C1.toInt(), // Розовый
        0xFFFF8DA1.toInt(), // Темно-розовый
        0xFFE6C9A8.toInt(), // Светло-бежевый
        0xFF8B5A2B.toInt(), // Темно-коричневый
        0xFFDEB887.toInt(), // Песочный
        0xFFCD853F.toInt()  // Перу
    )

    // Предустановленные категории согласно требованиям пользователя
    val CATEGORIES = listOf(
        PredefinedCategory(
            id = 1,
            name = "Фильмы",
            iconResourceId = android.R.drawable.ic_media_play,
            color = COLORS[0]
        ),
        PredefinedCategory(
            id = 2,
            name = "Книги",
            iconResourceId = android.R.drawable.ic_menu_edit,
            color = COLORS[1]
        ),
        PredefinedCategory(
            id = 3,
            name = "Агенты",
            iconResourceId = android.R.drawable.ic_menu_myplaces,
            color = COLORS[2]
        ),
        PredefinedCategory(
            id = 4,
            name = "Генерации",
            iconResourceId = android.R.drawable.ic_menu_gallery,
            color = COLORS[3]
        ),
        PredefinedCategory(
            id = 5,
            name = "Важное",
            iconResourceId = android.R.drawable.ic_dialog_alert,
            color = COLORS[4]
        ),
        PredefinedCategory(
            id = 6,
            name = "Транспорт",
            iconResourceId = android.R.drawable.ic_menu_directions,
            color = COLORS[5]
        ),
        PredefinedCategory(
            id = 7,
            name = "Переписки",
            iconResourceId = android.R.drawable.ic_dialog_email,
            color = COLORS[6]
        ),
        PredefinedCategory(
            id = 8,
            name = "Другое",
            iconResourceId = android.R.drawable.ic_menu_more,
            color = COLORS[7]
        )
    )
}
