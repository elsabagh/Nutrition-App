package com.example.nutritionapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.nutritionapp.data.database.NutritionDatabase
import com.example.nutritionapp.data.database.data_food.NutritionData
import com.example.nutritionapp.repositories.NutritionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NutritionViewModel (application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<NutritionData>>
    private val repository: NutritionRepository

    init {
        val nutritionDao = NutritionDatabase.getDatabase(application).nutritionDao()
        repository = NutritionRepository(nutritionDao)
        readAllData = repository.readAllData
    }

    fun addData(nutritionData: NutritionData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addData(nutritionData)
        }
    }

}