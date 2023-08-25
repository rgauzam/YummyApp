package com.example.yummyapp.data.repository

import com.example.yummyapp.data.db.RecipeDao
import com.example.yummyapp.data.model.LocalMeal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


interface I_RecipesRepository {

    fun getRecipesOrderedAlphabetically(): Flow<List<LocalMeal>>

    suspend fun insertRecipe(item: LocalMeal)

    suspend fun deleteRecipe(item: LocalMeal)

}

@Singleton
class LocalRecipesRepository @Inject constructor(
    private val recipeDao: RecipeDao
) : I_RecipesRepository {
    override fun getRecipesOrderedAlphabetically(): Flow<List<LocalMeal>> =
        recipeDao.getRecipesOrderedAlphabetically()

    override suspend fun insertRecipe(item: LocalMeal) = recipeDao.insertRecipe(item)

    override suspend fun deleteRecipe(item: LocalMeal) = recipeDao.deleteRecipe(item)

}