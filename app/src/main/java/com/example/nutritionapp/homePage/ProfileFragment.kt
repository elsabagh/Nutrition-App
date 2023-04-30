package com.example.nutritionapp.homePage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nutritionapp.R
import com.example.nutritionapp.databinding.FragmentForgotpasswordBinding
import com.example.nutritionapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference

        val eName = binding.eName
        val eAge = binding.eAge
        val eWeight = binding.eWeight
        val eHeight = binding.eHeight
        val eEmail = binding.eEmail

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("User").child(userId).get().addOnSuccessListener {

            val name = it.child("name").value.toString()
            val age = it.child("age").value.toString()
            val weight = it.child("weight").value.toString()
            val height = it.child("height").value.toString()
            val email = it.child("email").value.toString()

            eName.text = name
            eAge.text = age
            eWeight.text = weight
            eHeight.text = height
            eEmail.text = email


            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener {

            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            Log.e("firebase", "Error getting data", it)
        }

    }

}