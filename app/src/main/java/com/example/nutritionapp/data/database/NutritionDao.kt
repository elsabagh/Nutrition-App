package com.example.nutritionapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nutritionapp.data.database.data_food.NutritionData


@Dao
interface NutritionDao {

    @Insert
    suspend fun addData(user: NutritionData)

//    @Query("SELECT * FROM nutritionFood ORDER BY id ASC")
//    fun readAllData(): LiveData<List<NutritionData>>

    @Query("SELECT * FROM nutritionFood WHERE meal = 'Breakfast';")
    fun readAllDataBreakfast(): LiveData<List<NutritionData>>

    @Query ("SELECT * FROM nutritionFood WHERE meal = 'Lunch';")
    fun readAllDataLunch(): LiveData<List<NutritionData>>

    @Query ("SELECT * FROM nutritionFood WHERE meal = 'Dinner';")
    fun readAllDataDinner(): LiveData<List<NutritionData>>

    @Query ("SELECT * FROM nutritionFood WHERE meal = 'Snacks';")
    fun readAllDataSnacks(): LiveData<List<NutritionData>>

}