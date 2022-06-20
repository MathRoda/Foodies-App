package com.example.foodies.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.foodies.database.MealDatabase
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.module.randommeal.RandomMeal
import com.example.foodies.network.FoodiesApi
import com.example.foodies.network.MealApiService
import com.example.foodies.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private var _mealDetail = MutableLiveData<Meal>()
    val mealDetail: LiveData<Meal> = _mealDetail


    fun getMealDetail(id: String) = viewModelScope.launch {
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
                _mealDetail.postValue(mealId)
            }
        } else
            Log.e("handling", response.message())

    }

    fun insertUpdate(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUpdate(meal)
        }
    }


}