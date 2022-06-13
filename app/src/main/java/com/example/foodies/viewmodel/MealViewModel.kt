package com.example.foodies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.module.randommeal.RandomMeal
import com.example.foodies.network.MealApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel: ViewModel() {

    private var _mealDetail = MutableLiveData<Meal>()
    val mealDetail: LiveData<Meal> = _mealDetail

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

}