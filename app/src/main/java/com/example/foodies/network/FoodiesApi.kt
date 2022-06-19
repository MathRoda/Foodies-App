package com.example.foodies.network

import com.example.foodies.module.categorymeal.CategoryList
import com.example.foodies.module.mostpopular.MostPopularMealList
import com.example.foodies.module.randommeal.RandomMeal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodiesApi {

    @GET("random.php")
    suspend fun getRandomMeal(): Response<RandomMeal>

    @GET("lookup.php?")
    suspend fun getMealsDetails(@Query("i") id:String): Response<RandomMeal>

    @GET("filter.php?")
    suspend fun getPopularItems(@Query("c") category: String): Response<MostPopularMealList>

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryList>

    @GET("search.php?")
    suspend fun getMealBySearch(@Query("s") s:String): Response<RandomMeal>
}