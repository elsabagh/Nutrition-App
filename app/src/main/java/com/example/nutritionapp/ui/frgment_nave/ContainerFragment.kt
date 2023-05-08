package com.example.nutritionapp.ui.frgment_nave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nutritionapp.R
import com.example.nutritionapp.databinding.FragmentContainerBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContainerFragment : Fragment() {

    private lateinit var bottomNavigationView: BottomNavigationView

    lateinit var binding: FragmentContainerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstFragmentView()

        bottomNavigationView = view.findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.navigation_dashboard -> {
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.navigation_result -> {
                    replaceFragment(MealsFragment())
                    true
                }
                R.id.navigation_more -> {
                    replaceFragment(MoreFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun firstFragmentView() {
        addFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun addFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, fragment)
        transaction.commit()
    }
}