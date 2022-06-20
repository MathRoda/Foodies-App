package com.example.foodies.di.modules

import android.content.Context
import com.example.foodies.data.database.MealDatabase
import com.example.foodies.data.remote.FoodiesApi
import com.example.foodies.repository.Repository
import com.example.foodies.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun providesFoodiesDatabase(
        @ApplicationContext context: Context
    ): MealDatabase = MealDatabase.getDatabase(context)

}