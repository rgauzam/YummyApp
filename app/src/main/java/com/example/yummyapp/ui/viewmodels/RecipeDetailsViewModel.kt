package com.example.yummyapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yummyapp.data.model.TransformedMeal
import com.example.yummyapp.data.repository.RecipesRepositoryI
import com.example.yummyapp.ui.navigation.Nav.IMAGE_DETAILS_ID_PARAM
import com.example.yummyapp.ui.uiStates.RecipeDetailsUiState
import com.example.yummyapp.ui.uiStates.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val INIT_ID = "{id}"

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
        launchUiAction(body = {
            val recipeDetail = recipesRepository.getRecipeDetails(mealId)
            _uiState.value = UIState.Success(
                recipeDetail.toUiState()
            )
        }, true)
    }

    internal fun chooseId(mealId: String): String {
        if(mealId == INIT_ID) {
            return recipesRepository.getLastIdDetails()
        }

        return mealId
    }

    fun clickSave(recipe: RecipeDetailsUiState) {
        launchUiAction(body = {
            val updatedRecipeDetail = recipesRepository.updateUiState(recipe.idMeal)
            _uiState.value = UIState.Success(
                updatedRecipeDetail.toUiState()
            )
        }, false)
    }

    private fun launchUiAction(body: suspend () -> Unit, withProgress: Boolean) {
        viewModelScope.launch {
            if (withProgress) {
                _uiState.value = UIState.Loading
            }
            try {
                body.invoke()
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e)
            }
        }
    }

    private fun TransformedMeal.toUiState(): RecipeDetailsUiState {
        return RecipeDetailsUiState(
            idMeal = idMeal,
            strMeal = strMeal,
            strCategory = strCategory,
            strArea = strArea,
            strInstructions = strInstructions,
            strMealThumb = strMealThumb,
            strIngredients = ingredients,
            isSaved = isSaved
        )
    }

}