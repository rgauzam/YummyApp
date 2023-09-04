package com.example.yummyapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.yummyapp.data.model.Ingredient
import com.example.yummyapp.data.model.TransformedMeal
import com.example.yummyapp.data.repository.LocalRecipesRepository
import com.example.yummyapp.data.repository.RecipesRepository
import com.example.yummyapp.ui.navigation.Nav.IMAGE_DETAILS_ID_PARAM
import com.example.yummyapp.ui.uiStates.RecipeDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val imagesRepository: RecipesRepository,
    private val localRecipesRepository: LocalRecipesRepository
) :
    ViewModel() {

    private val mealId: String = checkNotNull(savedStateHandle[IMAGE_DETAILS_ID_PARAM]).toString()

    private val _uiState: MutableStateFlow<RecipeDetailsUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<RecipeDetailsUiState?> = _uiState

    init {
        CoroutineScope(Dispatchers.Default).launch {
            runCatching {
                localRecipesRepository.getRecipeDetails(mealId)
            }.onSuccess { meal ->
                updateUiState(meal)
            }.getOrElse { _ ->
                runCatching {
                    imagesRepository.getRecipeDetails(mealId)
                }.onSuccess { meal ->
                    updateUiState(meal)
                }.getOrElse { e ->
                   throw e
                    //wyjebac i zrobic to w repo jak gadalismy i jest w notkach todo
                }
            }
        }
    }


    fun saveRecipeToDb(recipe: RecipeDetailsUiState) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                localRecipesRepository.getRecipeDetails(recipe.idMeal)
            } catch (e: IllegalArgumentException) {
                val transformedMeal = TransformedMeal(
                    idMeal = recipe.idMeal,
                    strMeal = recipe.strMeal,
                    strMealThumb = recipe.strMealThumb,
                    strCategory = recipe.strCategory,
                    strArea = recipe.strArea,
                    strInstructions = recipe.strInstructions,
                    ingredients = recipe.strIngredients,
                    isSaved = true
                )
                localRecipesRepository.insertRecipe(transformedMeal)
            }
        }
    }

    private fun updateUiState(meal: TransformedMeal) {
        val uiState = RecipeDetailsUiState(
            meal.idMeal,
            meal.strMeal,
            meal.strCategory,
            meal.strArea,
            meal.strInstructions,
            meal.strMealThumb,
            meal.ingredients,
            meal.isSaved
        )
        _uiState.value = uiState
    }

    fun removeRecipeFromDb(recipe: RecipeDetailsUiState) {
        CoroutineScope(Dispatchers.IO).launch {
            val transformedMeal = TransformedMeal(
                idMeal = recipe.idMeal,
                strMeal = recipe.strMeal,
                strMealThumb = recipe.strMealThumb,
                strCategory = recipe.strCategory,
                strArea = recipe.strArea,
                strInstructions = recipe.strInstructions,
                ingredients = recipe.strIngredients,
                isSaved = false
            )
            localRecipesRepository.deleteRecipe(transformedMeal)
        }
    }
}