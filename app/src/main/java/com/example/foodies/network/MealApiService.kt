package com.example.foodies.network

import com.example.foodies.module.categorymeal.CategoryList
import com.example.foodies.module.mostpopular.MostPopularMealList
import com.example.foodies.module.randommeal.RandomMeal
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

private val retrofitMeal = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface MealsApi {
    @GET("random.php")
    fun getRandomMeal():Call<RandomMeal>

    @GET("lookup.php?")
    fun getMealsDetails(@Query("i") id:String): Call<RandomMeal>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") category: String): Call<MostPopularMealList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") category: String): Call<MostPopularMealList>

}

object MealApiService {
    val retrofitInstance: MealsApi by lazy {
        retrofitMeal.create(MealsApi::class.java)
    }
}
