package com.example.nutritionapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritionapp.R
import com.example.nutritionapp.data.model.Meal
import com.example.nutritionapp.databinding.ItemMealBinding
import com.example.nutritionapp.ui.calculater.BestMealsFragment

class MealAdapter(val list: List<Meal>, private val listener: BestMealsFragment) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val currentMeal = list[position]
        holder.binding.apply {
            textMealName.text = currentMeal.name
            textCaloriesNumber.text = currentMeal.calories.toInt().toString()

            root.setOnClickListener { listener.onClickItem(currentMeal) }
        }
    }

    override fun getItemCount(): Int = list.size

    class MealViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = ItemMealBinding.bind(viewItem)
    }

}