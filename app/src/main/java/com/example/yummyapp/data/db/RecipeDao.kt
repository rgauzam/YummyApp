package com.example.yummyapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.yummyapp.data.model.LocalIngredient
import com.example.yummyapp.data.model.LocalMeal
import com.example.yummyapp.data.model.LocalMealWithIngredients
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert
    suspend fun insertMeal(localMeal: LocalMeal)

    @Delete
    suspend fun deleteMeal(localMeal: LocalMeal)

    @Insert
    suspend fun insertIngredient(localIngredient: LocalIngredient)

    @Query("DELETE FROM ingredients WHERE idMeal = :mealId")
    suspend fun deleteIngredientsForMeal(mealId: String)

    @Query("SELECT * FROM meals")
    fun loadLocalMealWithIngredients(): Flow<List<LocalMealWithIngredients>>


}

//@Dao
//interface IngredientDao {
//    @Insert
//    fun insertIngredient(ingredient: LocalIngredient)
//
//    @Query("SELECT * FROM ingredients WHERE mealId = :mealId")
//    fun getIngredientsForMeal(mealId: String): List<LocalIngredient>
//}

//    @Query("SELECT * FROM ingredients WHERE idMeal = :mealId")
//    fun getIngredientsForMeal(mealId: String): Flow<List<LocalIngredient>>


//    @Query("SELECT * FROM recipes")
//    fun loadLocalMealWithIngredients(): List<LocalMealWithIngredients>?


