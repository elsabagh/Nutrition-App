package com.example.nutritionapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritionapp.R
import com.example.nutritionapp.data.database.data_food.NutritionData
import com.example.nutritionapp.databinding.ItemBinding
import com.example.nutritionapp.databinding.ItemNutritiondataBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var nutritionDataList = emptyList<NutritionData>()

    class MyViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = nutritionDataList[position]
        holder.binding.foodName.text = currentItem.foodName
        holder.binding.textCalories.text = currentItem.calories
        holder.binding.textCarb.text = currentItem.carbs
        holder.binding.textFat.text = currentItem.fat
        holder.binding.textProtein.text = currentItem.protein

    }

    override fun getItemCount(): Int {
        return nutritionDataList.size
    }

    fun setData(nutritionData: List<NutritionData>){
        this.nutritionDataList = nutritionData
        notifyDataSetChanged()
    }
}