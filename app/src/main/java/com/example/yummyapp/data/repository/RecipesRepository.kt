package com.example.yummyapp.data.repository

import com.example.yummyapp.data.dataSoruce.RecipeLocalDataSource
import com.example.yummyapp.data.dataSoruce.RecipeRemoteDataSource
import com.example.yummyapp.data.model.TransformedMeal
import com.example.yummyapp.data.model.TransformedRecipesResponse
import com.example.yummyapp.data.model.getFirstMeal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


interface RecipesRepositoryI {
    suspend fun searchRecipes(query: String): TransformedRecipesResponse
    suspend fun updateUiState(id: String): TransformedMeal
    suspend fun getRecipeDetails(id: String): TransformedMeal
    fun getRecipes(): Flow<List<TransformedMeal>>
    suspend fun insertRecipe(transformedMeal: TransformedMeal)
    suspend fun deleteRecipe(transformedMeal: TransformedMeal)
    fun getLastIdDetails(): String
}

@Singleton
class RecipesRepository @Inject constructor(
    private val recipeRemoteDataSource: RecipeRemoteDataSource,
    private val recipeLocalDataSource: RecipeLocalDataSource
): RecipesRepositoryI {
    private lateinit var transformedRecipesResponse: TransformedRecipesResponse

    override suspend fun searchRecipes(query: String): TransformedRecipesResponse {
        transformedRecipesResponse = recipeRemoteDataSource.getRecipes(query)
        return transformedRecipesResponse
    }

    override suspend fun updateUiState(id: String): TransformedMeal {
        val localRecipe = getRecipeDetails(id)
        localRecipe.isSaved = !localRecipe.isSaved
        if (localRecipe.isSaved) {
            insertRecipe(localRecipe)
        } else {
            deleteRecipe(localRecipe)
        }
        return localRecipe
    }

    override suspend fun getRecipeDetails(id: String): TransformedMeal {
        recipeLocalDataSource.putLastId(id)
        val localResult: TransformedMeal? = recipeLocalDataSource.getRecipeDetails(id)
        if (localResult == null) {
            val result = recipeRemoteDataSource.getRecipesDetails(id)
            val firstMeal = result.getFirstMeal()
            if (firstMeal != null) {
                return firstMeal
            } else {
                throw Exception("Recipe not found for ID: $id")
            }
        }
        return localResult
    }

    override fun getRecipes(): Flow<List<TransformedMeal>> {
        return recipeLocalDataSource.getRecipes()
    }

    override suspend fun insertRecipe(transformedMeal: TransformedMeal) {
        recipeLocalDataSource.insertRecipe(transformedMeal)
    }

    override suspend fun deleteRecipe(transformedMeal: TransformedMeal) {
        recipeLocalDataSource.deleteRecipe(transformedMeal)
    }

    override fun getLastIdDetails(): String {
        val lastId = recipeLocalDataSource.getLastId()
        return lastId.toString()
    }
}


