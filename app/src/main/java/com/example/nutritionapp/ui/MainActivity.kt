package com.example.nutritionapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.nutritionapp.R
import com.example.nutritionapp.data.dataManager.MealDataManager
import com.example.nutritionapp.data.local.LocalStorage
import com.example.nutritionapp.data.model.Meal
import com.example.nutritionapp.util.Constants
import com.example.nutritionapp.util.parsers.CSVParser
import com.example.nutritionapp.util.parsers.CSVParserHealthAdvice
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}