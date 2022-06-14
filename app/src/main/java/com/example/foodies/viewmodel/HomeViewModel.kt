package com.example.foodies.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.foodies.database.MealDatabase
import com.example.foodies.module.categorymeal.Category
import com.example.foodies.module.categorymeal.CategoryList
import com.example.foodies.module.mostpopular.MostPopularMealList
import com.example.foodies.module.mostpopular.MostPopularMeal
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.module.randommeal.RandomMeal
import com.example.foodies.network.MealApiService
import com.example.foodies.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private var _randomMeal = MutableLiveData<Meal>()
    val randomMeal: LiveData<Meal> = _randomMeal

    private var _mostPopularMeal = MutableLiveData<List<MostPopularMeal>>()
    val mostPopularMeal: LiveData<List<MostPopularMeal>> = _mostPopularMeal

    private var _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories


    val getAllMeals: LiveData<List<Meal>>
    private val repository: Repository

    init {
        val mealsDao = MealDatabase.getDatabase(application).dao()
        repository = Repository(mealsDao)
        getAllMeals = repository.getAllMeals
    }




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