package com.example.yummyapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yummyapp.data.model.TransformedMeal
import com.example.yummyapp.data.repository.RecipesRepositoryI
import com.example.yummyapp.ui.uiStates.RecipeItemUiState
import com.example.yummyapp.ui.uiStates.SavedRecipesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedRecipesViewModel @Inject constructor(
    recipesRepository: RecipesRepositoryI
) : ViewModel() {

    val uiState: StateFlow<SavedRecipesUiState> = recipesRepository.getRecipes().map {
        SavedRecipesUiState(cr8UiStateFromResponse(it))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SavedRecipesUiState())

    private fun cr8UiStateFromResponse(recipesResponse: List<TransformedMeal>): List<RecipeItemUiState> {
        val itemsUiState = recipesResponse.map {
            RecipeItemUiState(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strMealThumb = it.strMealThumb
            )
        }
        return itemsUiState
    }
}

