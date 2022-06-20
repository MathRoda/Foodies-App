package com.example.foodies

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.example.foodies.activities.MealActivity
import com.example.foodies.databinding.FragmentSearchBinding
import com.example.foodies.ui.fragments.home.HomeFragment
import com.example.foodies.viewmodel.SearchViewModel


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private var mealId = ""
    private var mealStr = ""
    private var mealThumb = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)


        observeSearched()
        setOnMealCardClick()
        return binding.root
    }

    private fun setOnMealCardClick() {
        binding.searchedMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, mealId)
            intent.putExtra(HomeFragment.MEAL_NAME, mealStr)
            intent.putExtra(HomeFragment.MEAL_THUMB, mealThumb)

            startActivity(intent)
        }
    }

    private fun observeSearched() {
        binding.icSearch.setOnClickListener {
            onSearchClick()
            viewModel.searchedMeal.observe(viewLifecycleOwner, Observer {
                binding.apply {
                    imgSearchedMeal.load(it.strMealThumb)
                    tvSearchedMeal.text = it.strMeal
                    searchedMealCard.visibility = View.VISIBLE
                }
                mealId = it.idMeal
                mealStr = it.strMeal!!
                mealThumb = it.strMealThumb!!
            })
        }
    }

    private fun onSearchClick() {
      viewModel.getSearchedMeal(binding.edSearch.text.toString(), requireContext())
    }

}