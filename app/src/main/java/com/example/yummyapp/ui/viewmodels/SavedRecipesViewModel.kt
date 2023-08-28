package com.example.yummyapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yummyapp.data.model.TransformedMeal
import com.example.yummyapp.data.repository.LocalRecipesRepository
import com.example.yummyapp.ui.uiStates.RecipeItemUiState
import com.example.yummyapp.ui.uiStates.SavedRecipesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedRecipesViewModel @Inject constructor(
    localRecipesRepository: LocalRecipesRepository

) : ViewModel() {

    private val _recipes = localRecipesRepository.getRecipes().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
    )
    private val _uiState: MutableStateFlow<SavedRecipesUiState> = MutableStateFlow(
        SavedRecipesUiState()
    )
    val uiState: StateFlow<SavedRecipesUiState> = _uiState

    init {
        loadRecipes()
    }

    fun getRecipes(): StateFlow<List<TransformedMeal>> {
        return _recipes
    }

    fun loadRecipes() {
        viewModelScope.launch {
            getRecipes().collect { recipes ->
                val uiState = cr8UiStateFromResponse(recipes)
                _uiState.value = _uiState.value.copy(savedRecipes = uiState)
            }
        }
    }

    private fun cr8UiStateFromResponse(recipesResponse: List<TransformedMeal>): List<RecipeItemUiState> {
        val itemsUiState = recipesResponse.map {
            RecipeItemUiState(it.idMeal, it.strMeal, it.strMealThumb)
        }
        return itemsUiState
    }
}

