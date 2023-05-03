package com.example.nutritionapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nutritionapp.data.database.data_food.NutritionData

@Database(entities = [NutritionData::class], version = 1, exportSchema = false)
abstract class NutritionDatabase : RoomDatabase() {

    abstract fun nutritionDao(): NutritionDao

    companion object {
        @Volatile
        private var INSTANCE: NutritionDatabase? = null

        fun getDatabase(context: Context): NutritionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NutritionDatabase::class.java,
                    "nutrition_data"
                ).build()
                INSTANCE = instance
                instance

            }
        }
    }

}