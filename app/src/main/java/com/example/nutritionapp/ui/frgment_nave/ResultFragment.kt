package com.example.nutritionapp.ui.frgment_nave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.nutritionapp.R
import com.example.nutritionapp.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addBreakfast.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_resultFragment_to_breakFastFragment2)
        }
        binding.addLunch.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_launchFragment2)
        }
        binding.addDinner.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_dinnerFragment2)
        }
        binding.addSnack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_snacksFragment2)
        }
    }

}