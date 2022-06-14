package com.example.foodies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodies.module.categorymeal.Category
import com.example.foodies.module.mostpopular.MostPopularMeal
import com.example.foodies.module.mostpopular.MostPopularMealList
import com.example.foodies.network.MealApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel: ViewModel() {

    private var _meals = MutableLiveData<List<MostPopularMeal>>()
    val meals: LiveData<List<MostPopularMeal>> = _meals

    fun getMealsByCategory(category: String) {
        MealApiService.retrofitInstance.getPopularItems(category).enqueue(object : Callback<MostPopularMealList> {
            override fun onResponse(
                call: Call<MostPopularMealList>,
                response: Response<MostPopularMealList>
            ) {
                response.body()?.let {
                    _meals.postValue(it.meals)
                }
            }

            override fun onFailure(call: Call<MostPopularMealList>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }
        })
    }
}