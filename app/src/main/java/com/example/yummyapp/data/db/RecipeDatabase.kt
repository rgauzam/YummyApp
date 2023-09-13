package com.example.yummyapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yummyapp.data.model.LocalIngredient
import com.example.yummyapp.data.model.LocalMeal


@Database(
    entities = [LocalMeal::class, LocalIngredient::class],
    version = 2
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var Instance: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RecipeDatabase::class.java, "recipe_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
