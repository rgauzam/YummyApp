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
    private lateinit var transformedRecipesResponse: TransformedRecipesResponse

    suspend fun searchRecipes(query: String): TransformedRecipesResponse {
        transformedRecipesResponse = recipeRemoteDataSource.getRecipes(query)
        return transformedRecipesResponse
    }

    suspend fun updateUiState(id: String): TransformedMeal {
        val localRecipe = getRecipeDetails(id)
        localRecipe.isSaved = !localRecipe.isSaved
        if (localRecipe.isSaved) {
            insertRecipe(localRecipe)
        } else {
            deleteRecipe(localRecipe)
        }
        return localRecipe
    }

    suspend fun getRecipeDetails(id: String): TransformedMeal {
        recipeLocalDataSource.putLastId(id)
        val localResult: TransformedMeal? = recipeLocalDataSource.getRecipeDetails(id)
        if (localResult == null) {
            val result = recipeRemoteDataSource.getRecipesDetails(id)
            val firstMeal = result.getFirstMeal()
            if (firstMeal != null) {
                return firstMeal
            } else {
                return getRecipeDetails("53020")
            }
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
        return lastId.toString()
    }
}


