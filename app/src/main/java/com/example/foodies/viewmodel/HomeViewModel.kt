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

    private var _mostPopularMeal = MutableLiveData<Resources<List<MostPopularMeal>>>()
    val mostPopularMeal: LiveData<Resources<List<MostPopularMeal>>> = _mostPopularMeal

    private var _categories = MutableLiveData<Resources<List<Category>>>()
    val categories: LiveData<Resources<List<Category>>> = _categories

    private var _bottomSheetMeal = MutableLiveData<Resources<Meal>>()
    val bottomSheetMeal: LiveData<Resources<Meal>> = _bottomSheetMeal


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

    /**
     * handling getPopularItems request from FoodiesApi interface
     */

    fun getPopularItems() = viewModelScope.launch {
        try {
            val response = repository.getPopularItems("seafood")
            handlePopularItemResponse(response)
        }catch (e: Exception) {
            _randomMeal.postValue(Resources.Error(e.message.toString()))
        }
    }

    private fun handlePopularItemResponse(response: Response<MostPopularMealList>) {
        if (response.isSuccessful) {
            response.body()?.let {
                val popularItem = it.meals
                _mostPopularMeal.postValue(Resources.Success(popularItem))
            }
        } else
            _mostPopularMeal.postValue(Resources.Error(response.message()))
    }

    /**
     * handling getCategories request from FoodiesApi interface
     */

    fun getCategories() = viewModelScope.launch {
        try {
            val response = repository.getCategories()
            handleCategoriesResponse(response)

        } catch (e: Exception) {
            _categories.postValue(Resources.Error(e.message.toString()))
        }
    }

    private fun handleCategoriesResponse(response: Response<CategoryList>) {
        if (response.isSuccessful) {
            response.body()?.let {
                val category = it.categories
                _categories.postValue(Resources.Success(category))
            }
        } else
            _categories.postValue(Resources.Error(response.message()))
    }

    /**
     * handling getCategories request from FoodiesApi interface
     */

    fun getMealById(id: String) = viewModelScope.launch {
        try {
            val response = repository.getMealsDetails(id)
            handleMealByIdResponse(response)
        } catch (e: Exception) {
            _bottomSheetMeal.postValue(Resources.Error(e.message.toString()))
        }
    }

    private fun handleMealByIdResponse(response: Response<RandomMeal>) {
        if (response.isSuccessful) {
            response.body()?.let {
                val mealId = it.meals.first()
                _bottomSheetMeal.postValue(Resources.Success(mealId))
            }
        } else
            _bottomSheetMeal.postValue(Resources.Error(response.message()))
    }

    /**
     * handling the Database Functions (Insert&Update / Delete )
     */

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