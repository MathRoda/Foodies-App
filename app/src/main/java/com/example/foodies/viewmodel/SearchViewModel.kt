package com.example.foodies.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.module.randommeal.RandomMeal
import com.example.foodies.network.MealApiService
import com.example.foodies.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
)
    : ViewModel() {

    private var _searchedMeal = MutableLiveData<Meal>()
    val searchedMeal: LiveData<Meal> = _searchedMeal


    fun getSearchedMeal(search: String, context: Context)  = viewModelScope.launch {

    }

}