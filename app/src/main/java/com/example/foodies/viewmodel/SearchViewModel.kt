package com.example.foodies.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.module.randommeal.RandomMeal
import com.example.foodies.network.MealApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {

    private var _searchedMeal = MutableLiveData<Meal>()
    val searchedMeal: LiveData<Meal> = _searchedMeal


    fun getSearchedMeal(search: String, context: Context) {
        MealApiService.retrofitInstance.getMealBySearch(search).enqueue(object : Callback<RandomMeal> {
            override fun onResponse(call: Call<RandomMeal>, response: Response<RandomMeal>) {
               if (response.body()?.meals == null) {
                   Toast.makeText(context, "No Such Such Dish Found", Toast.LENGTH_SHORT)
               } else {
                   _searchedMeal.value = response.body()!!.meals.first()
               }
            }

            override fun onFailure(call: Call<RandomMeal>, t: Throwable) {
                Log.e("TAG", t.message.toString())
            }

        })
    }
}