package com.example.nutritionapp.ui.startup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.nutritionapp.R
import com.example.nutritionapp.databinding.FragmentSignUpBinding
import com.example.nutritionapp.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SignUpFragment : Fragment() {


    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentSignUpBinding

    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)

        binding.etPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val minLength = 8 // Set the minimum length to 8
                if (s?.length ?: 0 < minLength) {
                    binding.inputPassReg.error = "Minimum length is 8" // Display an error message
                } else {
                    binding.inputPassReg.error = null // Clear the error message
                }
            }
        })

        binding.etConfPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val minLength = 8 // Set the minimum length to 8
                if (s?.length ?: 0 < minLength) {
                    binding.inputConfPassReg.error =
                        "Minimum length is 8" // Display an error message
                } else {
                    binding.inputConfPassReg.error = null // Clear the error message
                }
            }
        })

        binding.textLog.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.btnReg.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPass.text.toString()
            val verifyPass = binding.etConfPass.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty()) {
                if (pass.length >= 8 && verifyPass.length >= 8) {
                    if (pass == verifyPass) {

                        registerUser(email, pass)

                    } else {
                        Toast.makeText(context, "Password is not same", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Password should be at least 8 characters long", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun registerUser(email: String, pass: String) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                saveDataUser()
                navController.navigate(R.id.action_signUpFragment_to_onBoardingFragment)

            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun saveDataUser() {

        val eEmail = binding.etEmail.text.toString().trim()

        val user = UserData(eEmail)

        val userId = mAuth.currentUser!!.uid

        database.child("User").child(userId).setValue(user)

    }


    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
    }

}