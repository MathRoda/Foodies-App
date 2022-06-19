package com.example.foodies.di.modules

import com.example.foodies.database.MealDatabase
import com.example.foodies.network.BASE_URL
import com.example.foodies.network.FoodiesApi
import com.example.foodies.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFoodiesApi(): FoodiesApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FoodiesApi::class.java)

    @Provides
    @Singleton
    fun providesRepository(
        foodiesApi: FoodiesApi,
        mealDatabase: MealDatabase
    ) = Repository(foodiesApi, mealDatabase)

}