package com.example.foodies.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.foodies.R
import com.example.foodies.adapters.CategoriesAdapter
import com.example.foodies.adapters.MostPopularMealAdapter
import com.example.foodies.databinding.FragmentHomeBinding
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.ui.activities.CategoriesActivity
import com.example.foodies.ui.activities.MealActivity
import com.example.foodies.ui.dialog.bottomsheet.MealBottomSheetFragment
import com.example.foodies.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var randomMeal: Meal
    private val viewModel: HomeViewModel by viewModels()
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

        onSearchIconClicked()

    }

    private fun onSearchIconClicked() {
        binding.iconSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
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
        viewModel.categories.observe(viewLifecycleOwner) {
            categoriesAdapter.setData(it)
        }
    }

    private fun prepareMostPopularRecyclerview() {
        binding.rvMostPopularMeals.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterMostPopular
        }
    }

    private fun onPopularItemClick() {
        adapterMostPopular.onMealClick = {
          startMealActivity()
        }
    }

    private fun observePopularItems() {
        viewModel.mostPopularMeal.observe(viewLifecycleOwner) {
            adapterMostPopular.setData(it)
        }
    }

    private fun onRandomMealClicked() {
        binding.randomImageFood.setOnClickListener {
            startMealActivity()
        }
    }

    private fun observeRandomMeal() {
        viewModel.randomMeal.observe(viewLifecycleOwner) {
           binding.randomImageFood.load(it.strMealThumb)
            this.randomMeal = it
        }
    }

    private fun startMealActivity() {
        val intent = Intent(activity, MealActivity::class.java)
        intent.putExtra(MEAL_ID, randomMeal.idMeal)
        intent.putExtra(MEAL_NAME, randomMeal.strMeal)
        intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
        startActivity(intent)
    }

}