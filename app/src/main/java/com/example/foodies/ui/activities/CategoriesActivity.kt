package com.example.foodies.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodies.adapters.MealsCategoriesAdapter
import com.example.foodies.databinding.ActivityCategoriesBinding
import com.example.foodies.ui.fragments.home.HomeFragment
import com.example.foodies.viewmodel.CategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding
    private val viewModel: CategoriesViewModel by viewModels()
    private lateinit var mealsAdapter: MealsCategoriesAdapter

    //info variables
    private lateinit var categoryTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealsAdapter = MealsCategoriesAdapter()
        prepareMealsByCategoriesRecyclerview()

        getMealInfoFromIntent()

        viewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_TITLE)!!)
        observeMeals()
    }

    private fun prepareMealsByCategoriesRecyclerview() {
        binding.rvCategoryMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = mealsAdapter
        }
    }

    private fun observeMeals() {
        viewModel.meals.observe(this) {
            binding.tvCategoryMealsCount.text = "$categoryTitle : ${it.size}"
            mealsAdapter.setData(it)
        }
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        categoryTitle = intent.getStringExtra(HomeFragment.CATEGORY_TITLE)!!
    }
}