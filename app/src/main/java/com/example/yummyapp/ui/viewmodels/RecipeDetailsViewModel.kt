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
    imagesRepository: RecipesRepository,
    private val localRecipesRepository: LocalRecipesRepository  // Add this line
) :
    ViewModel() {

    private val mealId: String = checkNotNull(savedStateHandle[IMAGE_DETAILS_ID_PARAM]).toString()

    private val _uiState: MutableStateFlow<RecipeDetailsUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<RecipeDetailsUiState?> = _uiState

    init {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                imagesRepository.getRecipesDetails(mealId)?.let {
                    val uiState = RecipeDetailsUiState(
                        it.idMeal,
                        it.strMeal,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.ingredients
                    )
                    _uiState.value = uiState
                }
            } catch (e: Exception) {

            }
        }
    }

    fun saveRecipeToDb(recipe: RecipeDetailsUiState) {
        CoroutineScope(Dispatchers.IO).launch {
            val transformedMeal = TransformedMeal(
                idMeal = recipe.idMeal,
                strMeal = recipe.strMeal,
                strMealThumb = recipe.strMealThumb,
                strCategory = recipe.strCategory,
                strArea = recipe.strArea,
                strInstructions = recipe.strInstructions,
                ingredients = recipe.strIngredients
            )
            localRecipesRepository.insertRecipe(transformedMeal)
        }
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
                ingredients = recipe.strIngredients
            )
            localRecipesRepository.deleteRecipe(transformedMeal)
        }
    }
}