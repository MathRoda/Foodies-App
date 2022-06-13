package com.example.foodies.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import coil.load
import com.example.foodies.R
import com.example.foodies.databinding.ActivityMealBinding
import com.example.foodies.fragments.home.HomeFragment
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

    }

    private fun onYoutubeIconClicked() {
       binding.youtubeIcon.setOnClickListener {
           val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
           startActivity(intent)
       }
    }

    private fun observeMealDetails() {
        viewModel.mealDetail.observe(this){
          binding.tvCategory.text = it!!.strCategory
          binding.tvLocation.text = it.strArea
          binding.tvInstructionsSteps.text = it.strInstructions
          youtubeLink = it.strYoutube
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