package com.example.foodies.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.foodies.R
import com.example.foodies.databinding.ActivityMealBinding
import com.example.foodies.ui.fragments.home.HomeFragment
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private val viewModel: MealViewModel by viewModels()

    //info variables
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealInfoFromIntent()
        setInfoInViews()
        onLoadingCase()
        viewModel.getMealDetail(mealId)
        observeMealDetails()
        onYoutubeIconClicked()
        onFavoriteButtonClick()

    }

    private fun onFavoriteButtonClick() {
        binding.btnFavorite.setOnClickListener {
            meal?.let { viewModel.insertUpdate(it) }
            Toast
                .makeText(this, "You've favored this dish",Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun onYoutubeIconClicked() {
       binding.youtubeIcon.setOnClickListener {
           val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
           startActivity(intent)
       }
    }

    private var meal: Meal? = null
    private fun observeMealDetails() {
        viewModel.mealDetail.observe(this){
          binding.tvCategory.text = it!!.strCategory
          binding.tvLocation.text = it.strArea
          binding.tvInstructionsSteps.text = it.strInstructions
          youtubeLink = it.strYoutube!!
          meal = it
          onResponseCase()
        }
    }

    private fun setInfoInViews() {
        binding.imgMealDetail.load(mealThumb)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun onLoadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnFavorite.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvLocation.visibility = View.INVISIBLE
        binding.youtubeIcon.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnFavorite.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvLocation.visibility = View.VISIBLE
        binding.youtubeIcon.visibility = View.VISIBLE
    }
}