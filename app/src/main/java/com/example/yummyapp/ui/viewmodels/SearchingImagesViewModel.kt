package com.example.yummyapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.yummyapp.data.model.RecipesResponse
import com.example.yummyapp.data.repository.RecipesRepository
import com.example.yummyapp.ui.uiStates.RecipeItemUiState
import com.example.yummyapp.ui.uiStates.SearchingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val exception: Throwable) : UIState<Nothing>()

}

@HiltViewModel
class SearchingImagesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipesRepository: RecipesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<SearchingUiState> =
        MutableStateFlow(SearchingUiState("chicken"))
    val uiState: StateFlow<SearchingUiState> = _uiState

    init {
        loadImages()
    }

    fun loadImages() {
        CoroutineScope(Dispatchers.Default).launch {
            val searchingText = _uiState.value.searchingText
//            _uiState.value = _uiState.value.copy(uiState = UIState.Loading)
//            try {
//                val response = searchImages(searchingText)
//                Log.e("sprawdzenie","czy jest polaczone"+response)
//                val uiState = cr8UiStateFromResponse(response)
//                _uiState.value = _uiState.value.copy(uiState = uiState)
//            } catch (e: Exception) {
//                _uiState.value = _uiState.value.copy(uiState = UIState.Error(e))
//            }
            val response = searchImages(searchingText)
                Log.e("sprawdzenie","czy jest polaczone"+response)
                val uiState = cr8UiStateFromResponse(response)
//                _uiState.value = _uiState.value.copy(uiState = uiState)
        }
    }
    fun updateState(uiState: SearchingUiState) {
        _uiState.value = uiState
    }

    private suspend fun searchImages(query: String): RecipesResponse {
        return recipesRepository.getImages(query)
    }

    private fun cr8UiStateFromResponse(searchImagesResponse: RecipesResponse): UIState.Success<List<RecipeItemUiState>> {
        val itemsUiState = searchImagesResponse.hits.map {
            RecipeItemUiState(it.name)
        }
            return UIState.Success(itemsUiState)
    }
}