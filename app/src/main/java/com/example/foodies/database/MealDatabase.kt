package com.example.foodies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodies.module.randommeal.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MealDatabase: RoomDatabase() {

    abstract fun dao(): Dao

    companion object {

        @Volatile
        private var INSTANCE: MealDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): MealDatabase {
            val tempInstance = INSTANCE
            tempInstance?.let {
                return tempInstance
            }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "meal_database"
                ).build()
                INSTANCE = instance
                return instance
        }
    }
}