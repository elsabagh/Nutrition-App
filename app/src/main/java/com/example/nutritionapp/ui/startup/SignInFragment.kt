package com.example.nutritionapp.ui.startup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.nutritionapp.R
import com.example.nutritionapp.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var mAuth : FirebaseAuth
    private lateinit var binding: FragmentSignInBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        mAuth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=findNavController()

        binding.etPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val minLength = 8 // Set the minimum length to 8
                if (s?.length ?: 0 < minLength) {
                    binding.inputPass.error = "Minimum length is 8" // Display an error message
                } else {
                    binding.inputPass.error = null // Clear the error message
                }
            }
        })

        binding.textReg.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.bbtnLog.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPass.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty())

                loginUser(email, pass)
            else
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
        }


        binding.pageForgetPass.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }

    }



    private fun loginUser(email: String, pass: String) {

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener() {
            if (it.isSuccessful) {
                val user = mAuth.currentUser
                updateUI(user)
                Log.d(TAG, "signIn:success")

            } else {
                Toast.makeText(context, "email or password is incorrect", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "signIn:failure", it.exception)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        navController.navigate(R.id.action_signInFragment_to_containerFragment)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance()
    }

    companion object {
        private const val TAG = "GoogleActivity"
    }
}