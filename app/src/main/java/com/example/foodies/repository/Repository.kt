package com.example.foodies.repository

import androidx.lifecycle.LiveData
import com.example.foodies.database.Dao
import com.example.foodies.module.randommeal.Meal

class Repository(private val dao: Dao) {

    val getAllMeals: LiveData<List<Meal>> = dao.getAllMeals()

    suspend fun insertUpdate(meal: Meal){
        dao.insertUpdate(meal)
    }

    suspend fun delete(meal: Meal){
        dao.delete(meal)
    }
}