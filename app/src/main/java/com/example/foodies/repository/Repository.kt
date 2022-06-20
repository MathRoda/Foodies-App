package com.example.foodies.repository

import com.example.foodies.data.database.MealDatabase
import com.example.foodies.module.randommeal.Meal
import com.example.foodies.data.remote.FoodiesApi

class Repository(
    private val foodiesApi: FoodiesApi,
    mealDatabase: MealDatabase
) {

    private val mealDao = mealDatabase.dao()

    // Api Functions
    suspend fun getRandomMeal() = foodiesApi.getRandomMeal()

    suspend fun getMealsDetails(
        id: String
    ) = foodiesApi.getMealsDetails(id)

    suspend fun getPopularItems(
        category: String
    ) = foodiesApi.getPopularItems(category)

    suspend fun getCategories() = foodiesApi.getCategories()

    suspend fun getMealBySearch(
        s: String
    ) = foodiesApi.getMealBySearch(s)

    // Database Functions
    suspend fun insertUpdate(
        meal: Meal
    ) = mealDao.insertUpdate(meal)

    suspend fun delete(
        meal: Meal
    ) = mealDao.delete(meal)

    fun getAllMeals() = mealDao.getAllMeals()
}