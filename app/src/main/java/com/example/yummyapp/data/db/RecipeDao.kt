package com.example.yummyapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.yummyapp.data.model.LocalMeal
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert
    suspend fun insertRecipe(localMeal: LocalMeal)

    @Delete
    suspend fun deleteRecipe(localMeal: LocalMeal)

    @Query("SELECT * FROM recipes ORDER BY strMeal ASC")
    fun getRecipesOrderedAlphabetically(): Flow<List<LocalMeal>>

    @Query("SELECT * FROM recipes ORDER BY strMeal ASC")
    fun getIngredients(): Flow<List<LocalMeal>>
}

//@Dao
//interface IngredientDao {
//    @Insert
//    fun insertIngredient(ingredient: LocalIngredient)
//
//    @Query("SELECT * FROM ingredients WHERE mealId = :mealId")
//    fun getIngredientsForMeal(mealId: String): List<LocalIngredient>
//}
