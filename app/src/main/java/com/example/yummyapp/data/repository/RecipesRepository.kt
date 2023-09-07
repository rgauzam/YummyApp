package com.example.yummyapp.data.repository

import com.example.yummyapp.data.dataSoruce.RecipeLocalDataSource
import com.example.yummyapp.data.dataSoruce.RecipeRemoteDataSource
import com.example.yummyapp.data.model.TransformedMeal
import com.example.yummyapp.data.model.TransformedRecipesResponse
import com.example.yummyapp.data.model.getFirstMeal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RecipesRepository @Inject constructor(
    private val recipeRemoteDataSource: RecipeRemoteDataSource,
    private val recipeLocalDataSource: RecipeLocalDataSource
) {

    //    private lateinit var recipesResponse: RecipesResponse
    private lateinit var transformedRecipesResponse: TransformedRecipesResponse  // wciaz nieogarniam czemu tu jest lateinit todo

    suspend fun searchRecipes(query: String): TransformedRecipesResponse {
        transformedRecipesResponse = recipeRemoteDataSource.getRecipes(query)
        return transformedRecipesResponse
    }

    suspend fun getRecipeDetails(id: String): TransformedMeal {
        val localResult: TransformedMeal? = recipeLocalDataSource.getRecipeDetails(id)
        if (localResult == null) {
            val result = recipeRemoteDataSource.getRecipesDetails(id)
            val firstMeal = result.getFirstMeal()
            if (firstMeal != null) {
                return firstMeal
            } else return getRecipeDetails("0")
        }
        return localResult
    }//  wciaz trzeba handle error todo

    fun getRecipes(): Flow<List<TransformedMeal>> {
        return recipeLocalDataSource.getRecipes()
    }

    suspend fun insertRecipe(transformedMeal: TransformedMeal) {
        recipeLocalDataSource.insertRecipe(transformedMeal)
    }

    suspend fun deleteRecipe(transformedMeal: TransformedMeal) {
        recipeLocalDataSource.deleteRecipe(transformedMeal)
    }

    fun getLastIdDetails(): String {
        val lastId = recipeLocalDataSource.getLastId()
        return lastId ?: "52841"
    }


}


