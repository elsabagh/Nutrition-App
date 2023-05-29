package com.example.nutritionapp.util

import com.example.nutritionapp.data.model.HealthAdvice
import com.example.nutritionapp.data.model.Meal

class Calculations {

    fun calculatePersonDataCalories(
        gender: Char,
        weight: Double,
        height: Double,
        age: Int
    ): Double {
        return when (gender) {
            Constants.KeyValues.MALE -> if (weight <= 0 || height <= 0 || age <= 0) 0.0
            else String.format("%.2f", 66.0 + (13.7 * weight) + (5.0 * height) - (6.8 * age))
                .toDouble()
            Constants.KeyValues.FEMALE -> if (weight <= 0 || height <= 0 || age <= 0) 0.0
            else String.format("%.2f", 665.0 + (9.6 * weight) + (1.8 * height) - (4.7 * age))
                .toDouble()
            else -> 0.0
        }

    }

    fun diabeticsBestMeals(mealsList: MutableList<Meal>, top: Int): List<Meal>? {
        if (mealsList.isEmpty() || top <= 0 || top > mealsList.size) return null
        mealsList.sortByDescending {
            it.potassium + 0.7 * it.carbohydrate + 0.5 * it.fiber - it.sugars
        }
        return mealsList.take(top)
    }

    fun bodyBuildingBestMeals(mealsList: MutableList<Meal>, top: Int): List<Meal>? {
        if (mealsList.isEmpty() || top <= 0 || top > mealsList.size) return null
        mealsList.sortByDescending {
            0.4 * it.protein + 0.15 * it.totalFat + 0.45 * it.carbohydrate
        }
        return mealsList.take(top)
    }


    fun weightLossBestMeals(mealsList: MutableList<Meal>, top: Int): List<Meal>? {

        if (mealsList.isEmpty() || top <= 0 || top > mealsList.size) return null
        mealsList.sortByDescending {
            0.5 * it.protein + 0.2 * it.totalFat + 0.3 * it.carbohydrate
        }
        return mealsList.take(top)
    }

    fun bloodPressureBestMeals(mealsList: MutableList<Meal>, top: Int): List<Meal>? {
        if (mealsList.isEmpty() || top <= 0 || top > mealsList.size) return null
        mealsList.sortByDescending {
            it.calcium + 0.7 * it.fiber - it.sodium - it.totalFat
        }
        return mealsList.take(top)
    }


}