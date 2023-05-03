package com.example.nutritionapp.homePage


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.nutritionapp.R
import com.example.nutritionapp.data.database.data_food.NutritionData
import com.example.nutritionapp.data.retrofit.EdamamApiClient
import com.example.nutritionapp.databinding.FragmentSearchBinding
import com.example.nutritionapp.viewModel.NutritionViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var mNutritionViewModel: NutritionViewModel
    lateinit var binding: FragmentSearchBinding
    val appId = "d722b2cb"
    val appKey = "6ce10335676e71e24798fde2e86d0b90"
    val s = "Calories Remaining: 1000"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        mNutritionViewModel = ViewModelProvider(this).get(NutritionViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.addButton.setOnClickListener {
            insertDataToDatabase()
        }

        CreateSpinner()


        binding.connection.text = s
        isInternetAvailable(activity?.applicationContext!!)
        binding.searchbut.setOnClickListener {
            if (isInternetAvailable(activity?.applicationContext!!) == true) {
                binding.connection.text = s
                search()
            } else {
                binding.connection.text = "0"
                binding.calories.text = "0"
                binding.Protien.text = "0"
                binding.carb.text = "0"
                binding.fats.text = "0"
                binding.itemname.text = "0"
            }

        }
    }

    fun CreateSpinner() {
        var Meals = arrayOf("Select a Meal","Breakfast", "Dinner", "Lunch", "Snacks")
        binding.MySpinner.adapter=
            ArrayAdapter<String>(requireContext(), R.layout.customspinner,Meals)
        binding.MySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.SelectedMeal.text=binding.MySpinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.SelectedMeal.text="Select a Meal"
            }

        }
    }

    private fun insertDataToDatabase() {
        val foodName = binding.itemname.text.toString()
        val calories = binding.calories.text.toString()
        val carb = binding.carb.text.toString()
        val protein = binding.Protien.text.toString()
        val fat = binding.fats.text.toString()
        val meal = binding.SelectedMeal.text.toString()
        if (calories !="0" && binding.SelectedMeal.text !="Select a Meal") {
            binding.addButton.isEnabled = true
            val nutrientData = NutritionData(0,foodName, "Calories:$calories kcl |","Carb:$carb g |","Fats:20 g ","Protien:$protein g |",meal)
            mNutritionViewModel.addData(nutrientData)
            Toast.makeText(requireContext(), "$foodName is Added", Toast.LENGTH_LONG).show()
        }
        else if (calories == "0")
        {
            Toast.makeText(requireContext(), "Nothing to Add", Toast.LENGTH_LONG).show()
        }
        else if (binding.SelectedMeal.text == "Select a Meal")
        {
            Toast.makeText(requireContext(), "Select a Meal", Toast.LENGTH_LONG).show()
        }
        else if (calories =="0" && binding.SelectedMeal.text =="Select a Meal")
        {
            Toast.makeText(requireContext(), "Nothing to Add", Toast.LENGTH_LONG).show()
        }
    }

//    private fun insertDataToDatabase() {
//        val foodName = binding.itemname.text.toString()
//        val calories = binding.calories.text.toString()
//        val carb = binding.carb.text.toString()
//        val fat = binding.fats.text.toString()
//        val protein = binding.Protien.text.toString()
//        val meal = binding.SelectedMeal.text.toString()
//
//        if (inputCheck(foodName, calories, carb, fat, protein)) {
//            val nutrientData = NutritionData(0, foodName, calories, carb, fat, protein)
//            mNutritionViewModel.addData(nutrientData)
//            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
//
//            findNavController().navigate(R.id.action_searchFragment_to_listFragment)
//        } else {
//            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
//                .show()
//        }
//    }
//
//    private fun inputCheck(
//        foodName: String,
//        calories: String,
//        carb: String,
//        fat: String,
//        protein: String
//    ): Boolean {
//        return !(TextUtils.isEmpty(foodName)
//                && TextUtils.isEmpty(calories)
//                && TextUtils.isEmpty(carb)
//                && TextUtils.isEmpty(fat)
//                && TextUtils.isEmpty(protein)
//                )
//    }

    fun search() {
        val ingredient = binding.searchbar.text.toString()
        lifecycleScope.launch {
            val call = EdamamApiClient.apiService.getNutrientInfo(appId, appKey, ingredient)
            if (call.isSuccessful) {
                binding.connection.text = s
                val nutrientResponse = call.body()
                val calories = nutrientResponse?.calories
                val protein = nutrientResponse?.totalNutrients?.protein?.quantity
                val fat = nutrientResponse?.totalNutrients?.fat?.quantity
                val carbohydrates = nutrientResponse?.totalNutrients?.carbohydrates?.quantity
                Log.i("nut", calories.toString())
                Log.i("nut", protein.toString())
                Log.i("nut", fat.toString())
                Log.i("nut", carbohydrates.toString())
                if (carbohydrates.toString() != "null") {
                    lifecycleScope.launch(Dispatchers.Main) {
                        binding.calories.text = calories.toString()
                        binding.Protien.text = protein.toString()
                        binding.carb.text = carbohydrates.toString()
                        binding.fats.text = fat.toString()
                        binding.itemname.text = ingredient
                    }
                } else {
                    binding.calories.text = "-"
                    binding.Protien.text = "-"
                    binding.carb.text = "-"
                    binding.fats.text = "-"
                    binding.itemname.text = "-"
                }
            }
        }
    }


    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }

}