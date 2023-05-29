package com.example.nutritionapp.ui.frgment_nave

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.nutritionapp.R
import com.example.nutritionapp.data.dataManager.MealDataManager
import com.example.nutritionapp.data.model.Meal
import com.example.nutritionapp.databinding.FragmentHomeBinding
import com.example.nutritionapp.ui.calculater.BestMealsFragment
import com.example.nutritionapp.util.*
import com.example.nutritionapp.util.enum.StateNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var database: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private var mealDataManager: Parcelable = MealDataManager()
    private lateinit var mealList: MutableList<Meal>
    private val bundle = Bundle()
    private val bestMealFragment = BestMealsFragment()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataLoad()

        buttonCardDiabetics()
        buttonCardGym()
        buttonCardPressure()
        buttonCardWeightLoss()
        buttonCalculateRequiredCaloriesFragment()

        binding.arrowButton.setOnClickListener { view ->
            if (binding.hiddenView.getVisibility() === View.VISIBLE) {
                TransitionManager.beginDelayedTransition(binding.cardview, AutoTransition())
                binding.hiddenView.setVisibility(View.GONE)
                binding.arrowButton.setImageResource(R.drawable.baseline_expand_more_24)
            } else {
                TransitionManager.beginDelayedTransition(binding.cardview, AutoTransition())
                binding.hiddenView.setVisibility(View.VISIBLE)
                binding.arrowButton.setImageResource(R.drawable.baseline_expand_less_24)
            }
        }

    }
    override fun onStart() {
        super.onStart()
        mealDataManager = requireNotNull(arguments?.getParcelable(Constants.KeyValues.Meal_DATA_MANAGER))
        mealList = (mealDataManager as MealDataManager).getMeals()
        bundle.putParcelable(Constants.KeyValues.Meal_DATA_MANAGER, mealDataManager)
    }

    private fun changeNavigation(state: StateNavigation, to: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        when (state) {
            StateNavigation.Add -> transaction.add(R.id.fragment_container, to)
            StateNavigation.Remove -> transaction.remove(to)
            StateNavigation.Replace -> transaction.replace(R.id.fragment_container, to)
        }
        transaction.addToBackStack(null).commit()
    }

    protected fun navigationTo(to: Fragment) {
        changeNavigation(StateNavigation.Replace, to)
    }

    protected fun backNavigation(to: Fragment) {
        changeNavigation(StateNavigation.Replace, to)
    }

    private fun buttonCardDiabetics() {
        binding.cardDiabetics.setOnClickListener {
            bundle.putString(Constants.KeyValues.BEST_MEAL_TYPE, Constants.KeyValues.DIABETICS)
            bestMealFragment.arguments = bundle
            navigationTo(bestMealFragment)

        }
    }

    private fun buttonCardGym() {
        binding.cardGym.setOnClickListener {
            bundle.putString(Constants.KeyValues.BEST_MEAL_TYPE, Constants.KeyValues.GYM)
            bestMealFragment.arguments = bundle
            navigationTo(bestMealFragment)
        }
    }


    private fun buttonCardPressure() {
        binding.cardPressure.setOnClickListener {
            bundle.putString(Constants.KeyValues.BEST_MEAL_TYPE, Constants.KeyValues.PRESSURE)
            bestMealFragment.arguments = bundle
            navigationTo(bestMealFragment)
        }
    }


    private fun buttonCardWeightLoss() {
        binding.cardWeightLoss.setOnClickListener {
            bundle.putString(Constants.KeyValues.BEST_MEAL_TYPE, Constants.KeyValues.WEIGHT_LOSS)
            bestMealFragment.arguments = bundle
            navigationTo(bestMealFragment)
        }
    }

    private fun buttonCalculateRequiredCaloriesFragment() {
        binding.calculateRequiredCalories.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_containerFragment_to_calculateRequiredCaloriesFragment)
        }
    }

    private fun dataLoad(){
        database = Firebase.database.reference
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("User").child(userId).get().addOnSuccessListener {

            val name = it.child("name").value.toString()
            val cal = it.child("calories").value.toString()
            val pro = it.child("protien").value.toString()
            val fats = it.child("fats").value.toString()
            val carb = it.child("carb").value.toString()

            binding.username.text="Welcome ${name} !"
            binding.dailyCalories.text="Your Daily Calories: ${cal} KCL"
            binding.CarbIntake.text="Your Daily Carb Intake: ${carb} grams"
            binding.protienIntake.text="Your Daily Protein Intake: ${pro} grams"
            binding.FatsIntake.text="Your Daily Fats Intake: ${fats} grams"


//            val currentTime = System.currentTimeMillis()
//            val endOfDay = getEndOfDay().timeInMillis
//            if (currentTime > endOfDay) {
//
//                val editMap = mapOf<String, Any?>(
//                    "reCal" to cal.toString(),
//                    "rePro" to pro.toString(),
//                    "reCarb" to fats.toString(),
//                    "reFats" to carb.toString()
//                )
//
//                val userId = FirebaseAuth.getInstance().currentUser!!.uid
//                database.child("User").child(userId).updateChildren(editMap)
//            }


            database.keepSynced(true)

        }
    }

//    fun getEndOfDay(): Calendar {
//        val calendar = Calendar.getInstance()
//        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY))
//        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE))
//        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND))
//        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND))
//        return calendar
//    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance()
    }

}