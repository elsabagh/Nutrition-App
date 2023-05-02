package com.example.nutritionapp.homePage

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.nutritionapp.data.NutritionDataF
import com.example.nutritionapp.data.retrofit.EdamamApiClient
import com.example.nutritionapp.databinding.FragmentDiaryBinding
import com.example.nutritionapp.viewModel.NutritionViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
//    private lateinit var mNutritionViewModel: NutritionViewModel
    lateinit var binding: FragmentDiaryBinding
    val appId = "d722b2cb"
    val appKey = "6ce10335676e71e24798fde2e86d0b90"
    val s = "Calories Remaining: 1000"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiaryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

//        mNutritionViewModel = ViewModelProvider(this).get(NutritionViewModel::class.java)

        binding.addButton.setOnClickListener {
//            insertDataToDatabase()
            SaveDataNutrition()

        }


        binding.connection.text = s
        isInternetAvailable(activity?.applicationContext!!)
        binding.searchbut.setOnClickListener {
            if (isInternetAvailable(activity?.applicationContext!!) == true) {
                binding.connection.text = s
                search()
            } else {
                binding.connection.text = "Please Cheak Your Internet Connection"
                binding.calories.text = "-"
                binding.protein.text = "-"
                binding.carb.text = "-"
                binding.fats.text = "-"
                binding.itemName.text = "-"
            }

        }
    }

    private fun SaveDataNutrition() {
        val dName = binding.itemName.text.toString()
        val dCal = binding.calories.text.toString()
        val dCarb = binding.carb.text.toString()
        val dFat = binding.fats.text.toString()
        val dProtein = binding.protein.text.toString()

        val dataNutrition = NutritionDataF(dName, dCal, dCarb, dFat, dProtein)
//
//        if (dName.isEmpty()){
//            etName.error = "name"
//        }
//        if (dCal.isEmpty()){
//            etCal.error = "cal"
//        }
//        if (dCarb.isEmpty()){
//            etCarb.error = "carb"
//        }
//        if (dFat.isEmpty()){
//            etFat.error = "fat"
//        }
//        if (dProtein.isEmpty()){
//            etProtein.error = "protein"
//        }

        database.push().setValue(dataNutrition).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

            }
        }
    }

//    private fun insertDataToDatabase() {
//        val foodName = binding.itemName.text.toString()
//        val calories = binding.calories.text.toString()
//        val carb = binding.carb.text.toString()
//        val fat = binding.fats.text.toString()
//        val protein = binding.Protien.text.toString()
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
                        binding.protein.text = protein.toString()
                        binding.carb.text = carbohydrates.toString()
                        binding.fats.text = fat.toString()
                        binding.itemName.text = ingredient
                    }
                } else {
                    binding.calories.text = "-"
                    binding.protein.text = "-"
                    binding.carb.text = "-"
                    binding.fats.text = "-"
                    binding.itemName.text = "-"
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

    private fun init(view: View) {

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("NutritionData")
            .child(mAuth.currentUser?.uid.toString())
    }
}