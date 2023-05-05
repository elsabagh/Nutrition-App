package com.example.nutritionapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.nutritionapp.R
import com.example.nutritionapp.model.NutritionDataF
import com.example.nutritionapp.model.retrofit.EdamamApiClient
import com.example.nutritionapp.databinding.FragmentSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    lateinit var binding: FragmentSearchBinding
    val appId = "d722b2cb"
    val appKey = "6ce10335676e71e24798fde2e86d0b90"
    val s = "Calories Remaining: 1000"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        CreateSpinner()

        binding.addButton.setOnClickListener { SaveDataNutrition() }

        binding.connection.text = s
        isInternetAvailable(activity?.applicationContext!!)
        binding.searchbut.setOnClickListener {
            if (isInternetAvailable(activity?.applicationContext!!) == true) {
                binding.connection.text = s
                search()
            }
            else {
                binding.connection.text = "Please Cheak Your Internet Connection"
                binding.calories.text = "-"
                binding.Protien.text = "0"
                binding.carb.text = "0"
                binding.fats.text = "0"
                binding.itemname.text = "0"
            }

        }
    }

    //Create Spinner
    fun CreateSpinner() {
        var Meals = arrayOf("Select a Meal", "Breakfast", "Lunch","Dinner","Snacks")
        binding.MySpinner.adapter =
            ArrayAdapter<String>(requireContext(), R.layout.customspinner, Meals)
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

    //Save Data
    private fun SaveDataNutrition() {
        val dName = binding.itemname.text.toString()
        val dCal = binding.calories.text.toString()
        val dCarb = binding.carb.text.toString()
        val dFat = binding.fats.text.toString()
        val dProtein = binding.Protien.text.toString()
        val dSelectedMeal = binding.SelectedMeal.text.toString()
        if (dCal !="0" && binding.SelectedMeal.text !="Select a Meal") {
            val dataNutrition = NutritionDataF(dName, dCal, dCarb, dFat, dProtein, dSelectedMeal)
            database.push().setValue(dataNutrition).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "$dName is Added", Toast.LENGTH_LONG).show()
                }
            }
        }
        else if (dCal == "0")
        {
            Toast.makeText(requireContext(), "Nothing to Add", Toast.LENGTH_LONG).show()
        }
        else if (binding.SelectedMeal.text == "Select a Meal")
        {
            Toast.makeText(requireContext(), "Select a Meal", Toast.LENGTH_LONG).show()
        }
        else if (dCal =="0" && binding.SelectedMeal.text =="Select a Meal")
        {
            Toast.makeText(requireContext(), "Nothing to Add", Toast.LENGTH_LONG).show()
        }
    }

    // Search For a Food
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
                }
                else {
                    binding.calories.text = "0"
                    binding.Protien.text = "0"
                    binding.carb.text = "0"
                    binding.fats.text = "0"
                    binding.itemname.text = "0"
                }
            }
        }
    }


    //Check Internet Availability
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

    // Initialize Firebase
    private fun init(view: View) {

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("NutritionData")
            .child(mAuth.currentUser?.uid.toString())
    }
}