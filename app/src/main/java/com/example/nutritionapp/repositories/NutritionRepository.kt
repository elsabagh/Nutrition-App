package com.example.nutritionapp.repositories

import androidx.lifecycle.LiveData
import com.example.nutritionapp.data.database.NutritionDao
import com.example.nutritionapp.data.database.NutritionDatabase
import com.example.nutritionapp.data.database.data_food.NutritionData

class NutritionRepository(private val nutritionDao: NutritionDao) {

    val readAllData: LiveData<List<NutritionData>> = nutritionDao.readAllData()

    suspend fun addData(nutritionData: NutritionData){
        nutritionDao.addData(nutritionData)
    }
}