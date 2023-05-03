package com.example.nutritionapp.repositories

import androidx.lifecycle.LiveData
import com.example.nutritionapp.data.database.NutritionDao
import com.example.nutritionapp.data.database.NutritionDatabase
import com.example.nutritionapp.data.database.data_food.NutritionData

class NutritionRepository(private val nutritionDao: NutritionDao) {

//    val readAllData: LiveData<List<NutritionData>> = nutritionDao.readAllData()

    val readAllDataBreakFast: LiveData<List<NutritionData>> = nutritionDao.readAllDataBreakfast()
    val readAllDataLunch: LiveData<List<NutritionData>> = nutritionDao.readAllDataLunch()
    val readAllDataDinner: LiveData<List<NutritionData>> = nutritionDao.readAllDataDinner()
    val readAllDataSnacks: LiveData<List<NutritionData>> = nutritionDao.readAllDataSnacks()

    suspend fun addData(nutritionData: NutritionData){
        nutritionDao.addData(nutritionData)
    }
}