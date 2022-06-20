package com.example.foodies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodies.module.categorymeal.Category
import com.example.foodies.module.mostpopular.MostPopularMeal
import com.example.foodies.module.mostpopular.MostPopularMealList
import com.example.foodies.network.MealApiService
import com.example.foodies.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private var _meals = MutableLiveData<List<MostPopularMeal>>()
    val meals: LiveData<List<MostPopularMeal>> = _meals

    fun getMealsByCategory(category: String)  = viewModelScope.launch {
        val response = repository.get()
    }
}