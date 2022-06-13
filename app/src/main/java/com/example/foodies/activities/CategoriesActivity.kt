package com.example.foodies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodies.R
import com.example.foodies.adapters.MealsCategoriesAdapter
import com.example.foodies.databinding.ActivityCategoriesBinding
import com.example.foodies.fragments.home.HomeFragment
import com.example.foodies.viewmodel.CategoriesViewModel

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
        viewModel.meals.observe(this, Observer {
            binding.tvCategoryMealsCount.text = "$categoryTitle : ${it.size}"
            mealsAdapter.setData(it)
        })
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        categoryTitle = intent.getStringExtra(HomeFragment.CATEGORY_TITLE)!!
    }
}