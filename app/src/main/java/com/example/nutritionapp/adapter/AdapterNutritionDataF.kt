package com.example.nutritionapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritionapp.R
import com.example.nutritionapp.data.NutritionDataF
import com.example.nutritionapp.databinding.ItemNutritiondataBinding
import java.util.ArrayList

class AdapterNutritionDataF(private val nutritionDataList: ArrayList<NutritionDataF>) :
    RecyclerView.Adapter<AdapterNutritionDataF.MyViewHolder>() {

    class MyViewHolder(val binding: ItemNutritiondataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tvNameFood = binding.foodName
        val tvCal = binding.textCalories
        val tvCarb = binding.textCarb
        val tvFat = binding.textFat
        val tvProtein = binding.textProtein


//        val tvNameFood: TextView = itemView.findViewById(R.id.foodName)
//        val tvCal: TextView = itemView.findViewById(R.id.calories)
//        val tvCarb: TextView = itemView.findViewById(R.id.carb)
//        val tvFat: TextView = itemView.findViewById(R.id.fats)
//        val tvProtein: TextView = itemView.findViewById(R.id.protein)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(ItemNutritiondataBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = nutritionDataList[position]
        holder.tvNameFood.text = currentItem.foodName
        holder.tvCal.text = currentItem.calories
        holder.tvCarb.text = currentItem.carbs
        holder.tvFat.text = currentItem.fat
        holder.tvProtein.text = currentItem.protein

    }

    override fun getItemCount(): Int {
        return nutritionDataList.size
    }

}