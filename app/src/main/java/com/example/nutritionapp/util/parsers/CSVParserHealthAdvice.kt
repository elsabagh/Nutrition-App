package com.example.nutritionapp.util.parsers

import android.util.Log
import com.example.nutritionapp.data.dataManager.HealthAdviceDataManger
import com.example.nutritionapp.data.model.HealthAdvice
import com.example.nutritionapp.util.Constants
import com.example.nutritionapp.util.extention.removeQuotations
import java.io.InputStreamReader

class CSVParserHealthAdvice {
    fun getHealthAdvicesFromCSV(buffer: InputStreamReader): HealthAdviceDataManger {
        val healthAdviceDataManger = HealthAdviceDataManger()
        buffer.forEachLine {
            val healthAdviceLineData = it.split(",")
            Log.v("SSF", healthAdviceLineData.toString())
            with(Constants.ColumnIndex) {
                healthAdviceDataManger.addHealthAdvice(
                    HealthAdvice(
                        title = healthAdviceLineData[TITLE],
                        details = healthAdviceLineData[DETAILS].removeQuotations()
                    )
                )
            }
        }
        return healthAdviceDataManger
    }
}