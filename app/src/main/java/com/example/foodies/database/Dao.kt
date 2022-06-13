package com.example.foodies.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.foodies.module.randommeal.Meal

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdate(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM meal_table")
    fun getAllMeals(): LiveData<List<Meal>>

}