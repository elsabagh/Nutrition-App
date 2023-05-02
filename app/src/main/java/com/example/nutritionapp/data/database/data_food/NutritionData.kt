package com.example.nutritionapp.data.database.data_food

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutritionFood")

data class NutritionData(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val foodName: String,
    val calories: String,
    val carbs: String,
    val fat: String,
    val protein: String,
)