package com.example.foodies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodies.module.categorymeal.Category
import com.example.foodies.module.categorymeal.CategoryList
import com.example.foodies.module.mostpopular.MostPopularMeal
import com.example.foodies.module.mostpopular.MostPopularMealList
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.module.randommeal.RandomMeal
import com.example.foodies.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private var _randomMeal = MutableLiveData<Meal>()
    val randomMeal: LiveData<Meal> = _randomMeal

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
            Log.e("catch", e.message.toString())
        }
    }

    private fun handleRandomMealResponse(response: Response<RandomMeal>) {
        if (response.isSuccessful) {
            response.body()?.let {
                val randomMeal = it.meals.first()
                _randomMeal.postValue(randomMeal)
            }
        } else
            Log.e("handling", response.message())

    }

    /**
     * handling getPopularItems request from FoodiesApi interface
     */

    fun getPopularItems() = viewModelScope.launch {
        try {
            val response = repository.getPopularItems("seafood")
            handlePopularItemResponse(response)
        }catch (e: Exception) {
            Log.e("catch", e.message.toString())
        }
    }

    private fun handlePopularItemResponse(response: Response<MostPopularMealList>) {
        if (response.isSuccessful) {
            response.body()?.let {
                val popularItem = it.meals
                _mostPopularMeal.postValue(popularItem)
            }
        } else
            Log.e("handling", response.message())

    }

    /**
     * handling getCategories request from FoodiesApi interface
     */

    fun getCategories() = viewModelScope.launch {
        try {
            val response = repository.getCategories()
            handleCategoriesResponse(response)

        } catch (e: Exception) {
            Log.e("catch", e.message.toString())
        }
    }

    private fun handleCategoriesResponse(response: Response<CategoryList>) {
        if (response.isSuccessful) {
            response.body()?.let {
                val category = it.categories
                _categories.postValue(category)
            }
        } else
            Log.e("handling", response.message())
    }

    /**
     * handling getCategories request from FoodiesApi interface
     */

    fun getMealById(id: String) = viewModelScope.launch {
        try {
            val response = repository.getMealsDetails(id)
            handleMealByIdResponse(response)
        } catch (e: Exception) {
            Log.e("catch", e.message.toString())
        }
    }

    private fun handleMealByIdResponse(response: Response<RandomMeal>) {
        if (response.isSuccessful) {
            response.body()?.let {
                val mealId = it.meals.first()
                _bottomSheetMeal.postValue(mealId)
            }
        } else
            Log.e("handling", response.message())
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