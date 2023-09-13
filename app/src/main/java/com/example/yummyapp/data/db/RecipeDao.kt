package com.example.yummyapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.yummyapp.data.model.LocalIngredient
import com.example.yummyapp.data.model.LocalMeal
import com.example.yummyapp.data.model.LocalMealWithIngredients
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert
    suspend fun insertMeal(localMeal: LocalMeal)

    @Query("DELETE FROM meals WHERE idMeal = :mealId")
    suspend fun deleteMeal(mealId: String)

    @Insert
    suspend fun insertIngredient(localIngredient: LocalIngredient)

    @Query("DELETE FROM ingredients WHERE idMeal = :mealId")
    suspend fun deleteIngredientsForMeal(mealId: String)

    @Query("SELECT * FROM meals")
    fun loadLocalMealWithIngredients(): Flow<List<LocalMealWithIngredients>>

    @Transaction
    @Query("SELECT * FROM meals WHERE idMeal = :id")
    suspend fun loadMealWithIngredientsById(id: String): LocalMealWithIngredients?


}

