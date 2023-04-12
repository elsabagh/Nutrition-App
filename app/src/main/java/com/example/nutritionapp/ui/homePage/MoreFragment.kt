package com.example.nutritionapp.ui.homePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.nutritionapp.R
import com.example.nutritionapp.databinding.FragmentHomeBinding
import com.example.nutritionapp.databinding.FragmentMoreBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MoreFragment : Fragment() {

    lateinit var binding: FragmentMoreBinding
    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)

        binding.imageIconLogout.setOnClickListener {

            Firebase.auth.signOut()
            navController.navigate(R.id.action_containerFragment_to_signInFragment)

            Toast.makeText(context, "Logout success", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance()
    }

}