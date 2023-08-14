package com.example.yummyapp.data.repository

import com.example.yummyapp.data.dataSoruce.RecipeRemoteDataSource
import com.example.yummyapp.data.model.Meal
import com.example.yummyapp.data.model.RecipesResponse
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RecipesRepository @Inject constructor(private val recipeRemoteDataSource: RecipeRemoteDataSource) {

    private lateinit var recipesResponse: RecipesResponse
    suspend fun getRecipes(query: String): RecipesResponse {
        recipesResponse = recipeRemoteDataSource.getRecipes(query)
        return recipesResponse
    }

    suspend fun getRecipesDetails(id: String): Meal {
        recipesResponse.meals.find {
            it.idMeal == id
        }?.let {
            return it
        }

        return recipeRemoteDataSource.getRecipesDetails(id).meals.get(0)
    }

}