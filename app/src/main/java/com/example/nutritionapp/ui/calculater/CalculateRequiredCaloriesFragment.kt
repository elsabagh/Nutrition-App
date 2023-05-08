package com.example.nutritionapp.ui.calculater


import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import com.example.nutritionapp.R
import com.example.nutritionapp.util.Calculations
import com.example.nutritionapp.databinding.FragmentCalculateRequiredCaloriesBinding
import com.example.nutritionapp.ui.base.BaseFragment
import com.example.nutritionapp.util.Constants
import com.example.nutritionapp.util.enum.Operation
import com.example.nutritionapp.util.enum.StateNumber
import com.example.nutritionapp.util.extention.validityNumber
import com.example.nutritionapp.util.istVisible


class CalculateRequiredCaloriesFragment : BaseFragment<FragmentCalculateRequiredCaloriesBinding>()  {

    override fun bindingInflater(): FragmentCalculateRequiredCaloriesBinding =
        FragmentCalculateRequiredCaloriesBinding.inflate(layoutInflater)

//    override var visibilityCustomActionBar: Boolean = true
//    override var visibilityBackButton: Boolean = true
//    override fun getTitle(): String = getString(R.string.required_calories)

    private val calculations = Calculations()

    var visibilityResult: Boolean = true
    var gender: Char? = null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun setUp() {
        visibleResultText(false)
        initValues()
        initListener()
        resultCalculateRequiredCalories()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initValues(){
        visibilityResult = true
        setValueWeight()
        setValueHeight()
        setValueAge()
        genderSelectedColor()
    }


    @SuppressLint("ClickableViewAccessibility", "ResourceAsColor", "NewApi",
        "UseCompatLoadingForDrawables"
    )
    private fun initListener(){

        binding.plusButtonWeight.setOnTouchListener{ _, _ ->
            val value = changeValues(binding.textValueWeight.text.toString(),Operation.PLUS)
            setValueWeight(value)
            true
        }

        binding.minButtonWeight.setOnTouchListener { _, _ ->
            val value = changeValues(binding.textValueWeight.text.toString(), Operation.MINIS)
            setValueWeight(value)
            true
        }


        binding.plusButtonHeight.setOnTouchListener{ _, _ ->
            val value = changeValues(binding.textValueHeight.text.toString(),Operation.PLUS)
            setValueHeight(value)
            true
        }

        binding.minButtonHeight.setOnTouchListener{ _, _ ->
            val value = changeValues(binding.textValueHeight.text.toString(),Operation.MINIS)
            setValueHeight(value)
            true
        }


        binding.plusButtonAge.setOnTouchListener{ _, _ ->
            val value = changeValues(binding.textValueAge.text.toString(),Operation.PLUS)
            setValueAge(value)
            true
        }

        binding.minButtonAge.setOnTouchListener{ _, _ ->
            val value = changeValues(binding.textValueAge.text.toString(),Operation.MINIS)
            setValueAge(value)
            true
        }


        binding.cardMale.setOnClickListener{
            gender = Constants.KeyValues.MALE
            binding.imageMale.setBackgroundResource(R.drawable.ic_gender_male_selected)
            genderSelectedColor(R.color.primary_color,R.color.white,R.color.white,R.color.primary_color)

        }

        binding.cardFemale.setOnClickListener{
            gender = Constants.KeyValues.FEMALE
            binding.imageFemale.setBackgroundResource(R.drawable.ic_gender_female_selected)
            binding.imageFemale.isGone
            genderSelectedColor(R.color.white,R.color.primary_color,R.color.primary_color,R.color.white)

        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun genderSelectedColor(
        onSelectedMaleCardColor:Int = R.color.white,
        onSelectedMaleTextColor:Int = R.color.primary_color,
        onSelectedFeMaleCardColor:Int = R.color.white,
        onSelectedFeMaleTextColor:Int = R.color.primary_color){
        binding.apply {
            cardFemale.setCardBackgroundColor(requireContext().getColor(onSelectedFeMaleCardColor))
            textFemale.setTextColor(requireContext().getColor(onSelectedFeMaleTextColor))
            imageFemale.setColorFilter(requireContext().getColor(onSelectedFeMaleTextColor))
            cardMale.setCardBackgroundColor(requireContext().getColor(onSelectedMaleCardColor))
            textMale.setTextColor(requireContext().getColor(onSelectedMaleTextColor))
            imageMale.setColorFilter(requireContext().getColor(onSelectedMaleTextColor))
        }
    }


    private fun setValueWeight(value: Int = 0){
        binding.textValueWeight.text = value.toString()
    }

    private fun setValueHeight(value: Int = 0){
        binding.textValueHeight.text = value.toString()
    }

    private fun setValueAge(value: Int = 0){
        binding.textValueAge.text = value.toString()
    }

    private fun changeValues(value: String,operation: Operation): Int{
        var valueInput: Int =value.toInt()
        when(operation){
            Operation.PLUS ->  {
                if (valueInput.validityNumber(StateNumber.BIG))
                    valueInput++
            }
            Operation.MINIS -> {
                if (valueInput.validityNumber(StateNumber.SMALL))
                    valueInput--
            }
        }
        return valueInput
    }

    private fun visibleResultText(visible: Boolean){
        binding.resultCalculateRequiredCalories.visibility =  istVisible(visible)
    }


    @SuppressLint("NewApi")
    private fun resultCalculateRequiredCalories() {

        binding.buttonCalculateRequiredCalories.setOnClickListener {
            if (visibilityResult){
                val weight = binding.textValueWeight.text.toString().toInt()
                val height = binding.textValueHeight.text.toString().toInt()
                val age = binding.textValueAge.text.toString().toInt()


                if (weight != 0 && height != 0 && age != 0 && gender !=null) {
                    val result = calculations.calculatePersonDataCalories(
                        weight = weight.toDouble(),
                        height = height.toDouble(),
                        age = age,
                        gender = gender!!,
                    )
                    visibilityResult = false
                    binding.resultCalculateRequiredCalories.text = result.toString()

                    binding.textShow.text = getString(R.string.is_your_daily_required_calories)
                    visibleButton(false)
                    changeTheme()
                } else {
                    showToast(getString(R.string.please_inter_info))
                }
            }
            else{
                binding.textShow.text = getString(R.string.full_to_get_required_calories)
                initValues()
                changeTheme()
                visibleButton(true)
            }
        }

    }

    @SuppressLint("NewApi")
    private fun visibleButton(visible: Boolean){
        visibleResultText(!visible)

        binding.plusButtonWeight.visibility =  istVisible(visible)
        binding.minButtonWeight.visibility =  istVisible(visible)

        binding.plusButtonHeight.visibility =  istVisible(visible)
        binding.minButtonHeight.visibility =  istVisible(visible)

        binding.plusButtonAge.visibility =  istVisible(visible)
        binding.minButtonAge.visibility =  istVisible(visible)

        binding.gender.visibility =  istVisible(visible)

        if (gender ==  Constants.KeyValues.MALE)
        {
            binding.cardFemale.visibility =  istVisible(visible)
        }else{
            binding.cardMale.visibility =  istVisible(visible)
        }

    }

    @SuppressLint("NewApi")
    fun changeTheme(){
        val textLabel: Int?
        val color: Int?
        val backgroundColor: Int?

        if(visibilityResult){
            textLabel = R.string.get_your_required_calories
            color = R.color.white
            backgroundColor = R.color.primary_color
        }else{
            textLabel = R.string.reset_your_inputs
            color = R.color.primary_color
            backgroundColor = R.color.white
        }

        binding.buttonCalculateRequiredCalories.apply {
            text = getString(textLabel)
            setTextColor(requireContext().getColor(color))
            setBackgroundColor(requireContext().getColor(backgroundColor))
        }

    }

    private fun showToast(text: String){
        Toast.makeText(context, text , Toast.LENGTH_LONG).show()
    }

}