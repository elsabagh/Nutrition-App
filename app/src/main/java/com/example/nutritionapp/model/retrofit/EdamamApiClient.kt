package com.example.nutritionapp.model.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EdamamApiClient {
    val baseurl = "https://api.edamam.com/api/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseurl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(EdamamApiService::class.java)
}

//private val apiService by lazy { retrofit.create(EdamamApiService::class.java) }
//
//suspend fun getNutritionData(query: String): NutrientResponse? {
//    val response = apiService.getNutritionData(
//        appId = "a2d6a9e9", // Replace with your actual app ID
//        appKey = "41d46f9ea3b1ca4cc38209c28205eb0b", // Replace with your actual app key
//        query = query
//    )
//    return if (response.isSuccessful) response.body() else null
//}