package com.example.foodies.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.foodies.module.categorymeal.Category
import com.example.foodies.module.categorymeal.CategoryList
import com.example.foodies.module.mostpopular.MostPopularMeal
import com.example.foodies.module.mostpopular.MostPopularMealList
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.module.randommeal.RandomMeal
import com.example.foodies.network.MealApiService
import com.example.foodies.repository.Repository
import com.example.foodies.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private var _randomMeal = MutableLiveData<Resources<Meal>>()
    val randomMeal: LiveData<Resources<Meal>> = _randomMeal

    private var _mostPopularMeal = MutableLiveData<List<MostPopularMeal>>()
    val mostPopularMeal: LiveData<List<MostPopularMeal>> = _mostPopularMeal

    private var _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private var _bottomSheetMeal = MutableLiveData<Meal>()
    val bottomSheetMeal: LiveData<Meal> = _bottomSheetMeal


    /**
     * handling getRandomMeal request from FoodiesApi interface
     */
    fun getRandomMeal() = viewModelScope.launch {
        try {
            val response = repository.getRandomMeal()
            handleRandomMealResponse(response)
        } catch (e: Exception) {
            _randomMeal.postValue(Resources.Error(e.message.toString()))
        }
    }

    private fun handleRandomMealResponse(response: Response<RandomMeal>) {
        if (response.isSuccessful) {
            response.body()?.let {
                val randomMeal = it.meals[0]
                _randomMeal.postValue(Resources.Success(randomMeal))
            }
        } else
            _randomMeal.postValue(Resources.Error(response.message()))

    }

    fun getPopularItems(category: String) = viewModelScope.launch {
        try {
            val response = repository.getPopularItems("seafood")
            handlePopularItemResponse(response)
        }catch (e: Exception) {
            _randomMeal.postValue(Resources.Error(e.message.toString()))
        }
    }

    private fun handlePopularItemResponse(response: Response<MostPopularMealList>) {

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

    fun getMealById(id: String) {
        MealApiService.retrofitInstance.getMealsDetails(id).enqueue(object : Callback<RandomMeal>{
            override fun onResponse(call: Call<RandomMeal>, response: Response<RandomMeal>) {
                val meal = response.body()?.meals?.first()
                meal?.let {
                    _bottomSheetMeal.postValue(it)
                }
            }

            override fun onFailure(call: Call<RandomMeal>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }
        })
    }


    fun insertUpdate(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUpdate(meal)
        }
    }

    fun delete(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(meal)
        }
    }

}