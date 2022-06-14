package com.example.foodies.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.foodies.database.MealDatabase
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.module.randommeal.RandomMeal
import com.example.foodies.network.MealApiService
import com.example.foodies.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(application: Application): AndroidViewModel(application) {

    private var _mealDetail = MutableLiveData<Meal>()
    val mealDetail: LiveData<Meal> = _mealDetail

    private val repository: Repository

    init {
        val mealsDao = MealDatabase.getDatabase(application).dao()
        repository = Repository(mealsDao)
    }

    fun getMealDetail(id: String) {
        MealApiService.retrofitInstance.getMealsDetails(id).enqueue(object : Callback<RandomMeal> {
            override fun onResponse(call: Call<RandomMeal>, response: Response<RandomMeal>) {
                if (response.body() != null) {
                    _mealDetail.value = response.body()!!.meals[0]
                } else {
                    return
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


}