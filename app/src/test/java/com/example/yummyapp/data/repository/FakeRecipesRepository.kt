package com.example.yummyapp.data.repository

import com.example.yummyapp.data.model.Ingredient
import com.example.yummyapp.data.model.TransformedMeal
import com.example.yummyapp.data.model.TransformedRecipesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRecipesRepository : RecipesRepositoryI {

    internal val fakeData = mutableListOf<TransformedMeal>()

    init {
        val ingredientsSample = listOf(
            Ingredient("Salt", "1 tsp"),
            Ingredient("Pepper", "1/2 tsp"),
            Ingredient("Oil", "1 tbsp")
        )

        ('A'..'Z').forEachIndexed { index, c ->
            fakeData.add(
                TransformedMeal(
                    idMeal = index.toString(),
                    strMeal = "Meal $c",
                    strCategory = "Category $c",
                    strArea = "Area $c",
                    strInstructions = "Instructions for Meal $c",
                    strMealThumb = "URL for Meal $c",
                    ingredients = ingredientsSample,
                    isSaved = false
                )
            )
        }
        fakeData.shuffle()
    }

    override suspend fun searchRecipes(query: String): TransformedRecipesResponse {
        val filteredMeals = fakeData.filter { it.strMeal.contains(query, true) }
        return TransformedRecipesResponse(meals = filteredMeals)
    }

    override suspend fun updateUiState(id: String): TransformedMeal {
        val foundMeal = fakeData.find { it.idMeal == id }
            ?: throw Exception("Meal not found with ID: $id")

        foundMeal.isSaved = !foundMeal.isSaved
        return foundMeal
    }

    override suspend fun getRecipeDetails(id: String): TransformedMeal {
        return fakeData.find { it.idMeal == id }
            ?: throw Exception("Meal not found with ID: $id")
    }

    override fun getRecipes(): Flow<List<TransformedMeal>> {
        return flow { emit(fakeData) }
    }

    override suspend fun insertRecipe(transformedMeal: TransformedMeal) {
        fakeData.add(transformedMeal)
    }

    override suspend fun deleteRecipe(transformedMeal: TransformedMeal) {
        fakeData.remove(transformedMeal)
    }

    override fun getLastIdDetails(): String {
        return fakeData.lastOrNull()?.idMeal ?: ""
    }
}