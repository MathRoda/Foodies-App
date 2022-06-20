package com.example.foodies.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.module.randommeal.RandomMeal
import com.example.foodies.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository,
      application: Application
)
    : AndroidViewModel(application) {

    private var _searchedMeal = MutableLiveData<Meal>()
    val searchedMeal: LiveData<Meal> = _searchedMeal


    fun getSearchedMeal(search: String, context: Context)  = viewModelScope.launch {
        try {
            val response = repository.getMealBySearch(search)
            handleSearchResponse(response, context)
        } catch (e: Exception) {
            Log.e("catch", e.message.toString())
        }

    }

    private fun handleSearchResponse(response: Response<RandomMeal>, context: Context) {
        if (response.isSuccessful) {
            if (response.body() == null) {
              Toast.makeText(context, "No Such Meal, Try another one!!", Toast.LENGTH_SHORT).show()
            } else
               response.body()?.let {
                   _searchedMeal.postValue(it.meals.random())
               }
        } else
            Log.e("response", response.message())
    }

}