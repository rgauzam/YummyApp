package com.example.yummyapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yummyapp.data.repository.RecipesRepositoryI
import com.example.yummyapp.ui.navigation.Nav.IMAGE_DETAILS_ID_PARAM
import com.example.yummyapp.ui.uiStates.RecipeDetailsUiState
import com.example.yummyapp.ui.uiStates.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipesRepository: RecipesRepositoryI
) :
    ViewModel() {
    private val mealId: String = savedStateHandle.get<String>(IMAGE_DETAILS_ID_PARAM).toString()
    private val _uiState: MutableStateFlow<UIState<RecipeDetailsUiState>> =
        MutableStateFlow(UIState.Idle)
    val uiState: StateFlow<UIState<RecipeDetailsUiState>> = _uiState

    init {
        fetchRecipeDetails(chooseId(mealId))
    }

    internal fun fetchRecipeDetails(mealId: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val recipeDetail = recipesRepository.getRecipeDetails(mealId)
                _uiState.value = UIState.Success(
                    RecipeDetailsUiState(
                        idMeal = recipeDetail.idMeal,
                        strMeal = recipeDetail.strMeal,
                        strCategory = recipeDetail.strCategory,
                        strArea = recipeDetail.strArea,
                        strInstructions = recipeDetail.strInstructions,
                        strMealThumb = recipeDetail.strMealThumb,
                        strIngredients = recipeDetail.ingredients,
                        isSaved = recipeDetail.isSaved
                    )
                )
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e)
            }
        }
    }

    internal fun chooseId(mealId: String): String {
        var idToFetch = mealId
        if (idToFetch == "{id}") {
            idToFetch = recipesRepository.getLastIdDetails()
        }
        return idToFetch
    }

    fun clickSave(recipe: RecipeDetailsUiState) {
        viewModelScope.launch {
            try {
                _uiState.value = UIState.Loading
                val updatedRecipeDetail = recipesRepository.updateUiState(recipe.idMeal)
                _uiState.value = UIState.Success(
                    RecipeDetailsUiState(
                        idMeal = updatedRecipeDetail.idMeal,
                        strMeal = updatedRecipeDetail.strMeal,
                        strCategory = updatedRecipeDetail.strCategory,
                        strArea = updatedRecipeDetail.strArea,
                        strInstructions = updatedRecipeDetail.strInstructions,
                        strMealThumb = updatedRecipeDetail.strMealThumb,
                        strIngredients = updatedRecipeDetail.ingredients,
                        isSaved = updatedRecipeDetail.isSaved
                    )
                )
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e)
            }
        }
    }

}