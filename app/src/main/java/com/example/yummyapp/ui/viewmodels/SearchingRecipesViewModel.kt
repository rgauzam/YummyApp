package com.example.yummyapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yummyapp.data.model.TransformedRecipesResponse
import com.example.yummyapp.data.repository.RecipesRepositoryI
import com.example.yummyapp.ui.uiStates.RecipeItemUiState
import com.example.yummyapp.ui.uiStates.SearchUiState
import com.example.yummyapp.ui.uiStates.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchRecipesViewModel @Inject constructor(
    private val recipesRepository: RecipesRepositoryI
) :
    ViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(
        SearchUiState(
            uiState = UIState.Idle
        )
    )
    val uiState: StateFlow<SearchUiState> = _uiState

    fun loadImages() {
        viewModelScope.launch {
            val searchingText = _uiState.value.searchingText
            _uiState.value = _uiState.value.copy(uiState = UIState.Loading)
            try {
                val response = searchRecipes(searchingText)
                val uiState = response.cr8UiState()
                _uiState.value = _uiState.value.copy(uiState = uiState)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(uiState = UIState.Error(e))
            }
        }
    }

    fun updateState(uiState: SearchUiState) {
        _uiState.value = uiState
    }

    internal suspend fun searchRecipes(query: String): TransformedRecipesResponse {
        return recipesRepository.searchRecipes(query)
    }

    internal fun TransformedRecipesResponse.cr8UiState(): UIState.Success<List<RecipeItemUiState>> {
        val itemsUiState = meals.map {
            RecipeItemUiState(it.idMeal, it.strMeal, it.strMealThumb)
        }
        return UIState.Success(itemsUiState)
    }

}