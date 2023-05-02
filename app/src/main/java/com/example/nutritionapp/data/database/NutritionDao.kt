package com.example.nutritionapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nutritionapp.data.database.data_food.NutritionData


@Dao
interface NutritionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addData(user: NutritionData)

    @Query("SELECT * FROM nutritionFood ORDER BY id ASC")
    fun readAllData(): LiveData<List<NutritionData>>

}