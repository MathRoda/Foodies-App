package com.example.foodies.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.foodies.activities.CategoriesActivity
import com.example.foodies.activities.MealActivity
import com.example.foodies.adapters.CategoriesAdapter
import com.example.foodies.adapters.MostPopularMealAdapter
import com.example.foodies.databinding.FragmentHomeBinding
import com.example.foodies.fragments.bottomsheet.MealBottomSheetFragment
import com.example.foodies.fragments.home.HomeFragment.Companion.MEAL_ID
import com.example.foodies.fragments.home.HomeFragment.Companion.MEAL_NAME
import com.example.foodies.fragments.home.HomeFragment.Companion.MEAL_THUMB
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var randomMeal: Meal
    private lateinit var adapterMostPopular: MostPopularMealAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter


    companion object {
        const val MEAL_ID = "com.example.foodies.fragments.home mealId"
        const val MEAL_NAME = "com.example.foodies.fragments.home mealName"
        const val MEAL_THUMB = "com.example.foodies.fragments.home mealThumb"
        const val CATEGORY_TITLE = "com.example.foodies.fragments.home categoryTitle"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentHomeBinding.inflate(inflater, container, false)
        adapterMostPopular = MostPopularMealAdapter()
        categoriesAdapter = CategoriesAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareMostPopularRecyclerview()
        prepareCategoriesRecyclerview()

        viewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClicked()

        viewModel.getPopularItems()
        observePopularItems()
        onPopularItemClick()

        viewModel.getCategories()
        observeCategories()
        onCategoryItemClick()

        onPopularItemLongClick()

    }

    private fun onPopularItemLongClick() {
        adapterMostPopular.onLongMealClick = {
            val bottomSheet = MealBottomSheetFragment.newInstance(it.idMeal)
            bottomSheet.show(childFragmentManager, "Meals Info")
        }
    }

    private fun onCategoryItemClick() {
        categoriesAdapter.onItemClick = {
            val intent = Intent(activity, CategoriesActivity::class.java)
            intent.putExtra(CATEGORY_TITLE, it.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerview() {
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategories() {
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            categoriesAdapter.setData(it)
        })
    }

    private fun prepareMostPopularRecyclerview() {
        binding.rvMostPopularMeals.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterMostPopular
        }
    }

    private fun onPopularItemClick() {
        adapterMostPopular.onMealClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, it.idMeal)
            intent.putExtra(MEAL_NAME, it.strMeal)
            intent.putExtra(MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observePopularItems() {
        viewModel.mostPopularMeal.observe(viewLifecycleOwner, Observer {
            adapterMostPopular.setData(it)
        })
    }

    private fun onRandomMealClicked() {
        binding.randomImageFood.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        viewModel.randomMeal.observe(viewLifecycleOwner
        ) {
            binding.randomImageFood.load(it!!.strMealThumb)

            this.randomMeal = it
        }
    }

}