package com.example.foodies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodies.module.categorymeal.Category
import com.example.foodies.module.categorymeal.CategoryList
import com.example.foodies.module.mostpopular.MostPopularMealList
import com.example.foodies.module.mostpopular.MostPopularMeal
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.module.randommeal.RandomMeal
import com.example.foodies.network.MealApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private var _randomMeal = MutableLiveData<Meal>()
    val randomMeal: LiveData<Meal> = _randomMeal

    private var _mostPopularMeal = MutableLiveData<List<MostPopularMeal>>()
    val mostPopularMeal: LiveData<List<MostPopularMeal>> = _mostPopularMeal

    private var _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories




    fun getRandomMeal() {

            MealApiService.retrofitInstance.getRandomMeal().enqueue(object : Callback<RandomMeal> {
                override fun onResponse(call: Call<RandomMeal>, response: Response<RandomMeal>) {
                    response.body()?.let {
                        val randomMeal: Meal = it.meals[0]
                        _randomMeal.postValue(randomMeal)
                    }
                }

                override fun onFailure(call: Call<RandomMeal>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }
            })
        }

    fun getPopularItems() {
        MealApiService.retrofitInstance.getPopularItems("seafood").enqueue(object : Callback<MostPopularMealList> {
            override fun onResponse(call: Call<MostPopularMealList>, response: Response<MostPopularMealList>) {
                response.body()?.let {
                    _mostPopularMeal.postValue(it.meals)
                }
            }

            override fun onFailure(call: Call<MostPopularMealList>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }
        }
        )
    }

    fun getCategories() {
        MealApiService.retrofitInstance.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
              response.body()?.let {
                  _categories.postValue(it.categories)
              }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }
        })
    }

}