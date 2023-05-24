package com.example.nutritionapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritionapp.data.NutritionDataF
import com.example.nutritionapp.databinding.ItemBinding
import com.example.nutritionapp.ui.meals.BreakFastFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import java.util.ArrayList

class AdapterNutritionDataF(private val nutritionDataList: ArrayList<NutritionDataF>) :
    RecyclerView.Adapter<AdapterNutritionDataF.MyViewHolder>() {

    class MyViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val tvNameFood = binding.foodName
        val tvCal = binding.textCalories
        val tvCarb = binding.textCarb
        val tvFat = binding.textFat
        val tvProtein = binding.textProtein
        val tvDateTime = binding.textDate
        val deleteBtn = binding.btnDelete


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = nutritionDataList[position]
        holder.tvNameFood.text = currentItem.foodName
        holder.tvCal.text = currentItem.calories
        holder.tvCarb.text = currentItem.carbs
        holder.tvFat.text = currentItem.fat
        holder.tvProtein.text = currentItem.protein
        holder.tvDateTime.text = currentItem.timestamp


//        holder.deleteBtn.setOnClickListener {
//            MaterialAlertDialogBuilder(holder.itemView.context)
//                .setTitle("Delete item")
//                .setMessage("Are you sure")
//                .setPositiveButton("Yes") { _, _ ->
//                    val ref = FirebaseDatabase.getInstance().getReference("NutritionData")
//                        .child(FirebaseAuth.getInstance().currentUser?.uid.toString()).orderByChild("dataId")
//                    ref.removeValue()
//                        .addOnSuccessListener {
//                            Toast.makeText(holder.itemView.context, "removed", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                        .addOnFailureListener { error ->
//                            Toast.makeText(holder.itemView.context, "error", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                }
//                .setNegativeButton("No") { _, _ ->
//                    Toast.makeText(holder.itemView.context, "cancel", Toast.LENGTH_SHORT)
//                        .show()
//                }
//                .show()
//        }
    }

    override fun getItemCount(): Int {
        return nutritionDataList.size
    }

}