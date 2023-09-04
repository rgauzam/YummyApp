package com.example.yummyapp.data.repository

import com.example.yummyapp.data.db.RecipeDao
import com.example.yummyapp.data.model.Ingredient
import com.example.yummyapp.data.model.LocalIngredient
import com.example.yummyapp.data.model.LocalMeal
import com.example.yummyapp.data.model.TransformedMeal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


interface I_RecipesRepository {
    fun getRecipes(): Flow<List<TransformedMeal>>

    suspend fun insertRecipe(transformedMeal: TransformedMeal)

    suspend fun deleteRecipe(transformedMeal: TransformedMeal)

    suspend fun getRecipeDetails(id:String):TransformedMeal

}

@Singleton
class LocalRecipesRepository @Inject constructor(
    private val recipeDao: RecipeDao
) : I_RecipesRepository {
    override fun getRecipes(): Flow<List<TransformedMeal>> {
        return recipeDao.loadLocalMealWithIngredients().map { localMealsWithIngredientsList ->
            localMealsWithIngredientsList.map { mealWithIngredients ->
                TransformedMeal(
                    idMeal = mealWithIngredients.localMeal.idMeal,
                    strMeal = mealWithIngredients.localMeal.strMeal,
                    strCategory = mealWithIngredients.localMeal.strCategory,
                    strArea = mealWithIngredients.localMeal.strArea,
                    strInstructions = mealWithIngredients.localMeal.strInstructions,
                    strMealThumb = mealWithIngredients.localMeal.strMealThumb,
                    ingredients = mealWithIngredients.ingredients.map { ingredient ->
                        Ingredient(
                            strIngredient = ingredient.strIngredient,
                            strMeasure = ingredient.strMeasure
                        )
                    },
                    isSaved = true
                )
            }
        }
    }

    override suspend fun insertRecipe(transformedMeal: TransformedMeal) {
        val localMeal = LocalMeal(
            idMeal = transformedMeal.idMeal,
            strMeal = transformedMeal.strMeal,
            strMealThumb = transformedMeal.strMealThumb,
            strCategory = transformedMeal.strCategory,
            strArea = transformedMeal.strArea,
            strInstructions = transformedMeal.strInstructions
        )
        recipeDao.insertMeal(localMeal)

        for (ingredient in transformedMeal.ingredients) {
            val localIngredient = LocalIngredient(
                idMeal = transformedMeal.idMeal,
                strIngredient = ingredient.strIngredient,
                strMeasure = ingredient.strMeasure
            )
            recipeDao.insertIngredient(localIngredient)
        }
    }

    override suspend fun deleteRecipe(transformedMeal: TransformedMeal) {
        val localMeal = LocalMeal(
            idMeal = transformedMeal.idMeal,
            strMeal = transformedMeal.strMeal,
            strMealThumb = transformedMeal.strMealThumb,
            strCategory = transformedMeal.strCategory,
            strArea = transformedMeal.strArea,
            strInstructions = transformedMeal.strInstructions
        )
        recipeDao.deleteIngredientsForMeal(transformedMeal.idMeal)
        recipeDao.deleteMeal(localMeal)
    }

    override suspend fun getRecipeDetails(id: String): TransformedMeal {
        val mealWithIngredients = recipeDao.loadMealWithIngredientsById(id)
        mealWithIngredients?.let {
            return TransformedMeal(
                idMeal = it.localMeal.idMeal,
                strMeal = it.localMeal.strMeal,
                strCategory = it.localMeal.strCategory,
                strArea = it.localMeal.strArea,
                strInstructions = it.localMeal.strInstructions,
                strMealThumb = it.localMeal.strMealThumb,
                ingredients = it.ingredients.map { ingredient ->
                    Ingredient(
                        strIngredient = ingredient.strIngredient,
                        strMeasure = ingredient.strMeasure
                    )
                },
                isSaved = true
            )
        } ?: throw IllegalArgumentException("No meal found with id $id")
    }

}