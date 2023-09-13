package com.example.yummyapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yummyapp.data.model.TransformedMeal
import com.example.yummyapp.data.repository.RecipesRepository
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
    private val recipesRepository: RecipesRepository
) :
    ViewModel() {

    private val mealId: String = savedStateHandle.get<String>(IMAGE_DETAILS_ID_PARAM).toString()
//    private val _uiState: MutableStateFlow<RecipeDetailsUiState?> = MutableStateFlow(null)
//    val uiState: StateFlow<RecipeDetailsUiState?> = _uiState

    private val _uiState: MutableStateFlow<UIState<RecipeDetailsUiState>> =
        MutableStateFlow(UIState.Idle)
    val uiState: StateFlow<UIState<RecipeDetailsUiState>> = _uiState


    init {
        fetchRecipeDetails(chooseId(mealId))
    }

    private fun fetchRecipeDetails(mealId: String) {
        // Using viewModelScope, similar to loadImages() in SearchRecipesViewModel
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

//    init {
//        viewModelScope.launch {
//            try {
//                getRecipeDetails(chooseId(mealId))
//            } catch (e: Exception) {
//                //todo handle error
//            }
//        }
//    }
//
//    suspend fun getRecipeDetails(mealId: String) {
//        val recipeDetail = recipesRepository.getRecipeDetails(mealId)
//        _uiState.value = RecipeDetailsUiState(
//            idMeal = recipeDetail.idMeal,
//            strMeal = recipeDetail.strMeal,
//            strCategory = recipeDetail.strCategory,
//            strArea = recipeDetail.strArea,
//            strInstructions = recipeDetail.strInstructions,
//            strMealThumb = recipeDetail.strMealThumb,
//            strIngredients = recipeDetail.ingredients,
//            isSaved = recipeDetail.isSaved
//        )
//    }

    fun chooseId(mealId: String): String {
        var idToFetch = mealId
        if (idToFetch == "{id}") {
            idToFetch = recipesRepository.getLastIdDetails()
        }
        return idToFetch
    }

    //    fun clickSave(recipe: RecipeDetailsUiState) {
//        viewModelScope.launch {
//            val updatedRecipeDetail = recipesRepository.updateUiState(recipe.idMeal)
//            _uiState.value = RecipeDetailsUiState(
//                idMeal = updatedRecipeDetail.idMeal,
//                strMeal = updatedRecipeDetail.strMeal,
//                strCategory = updatedRecipeDetail.strCategory,
//                strArea = updatedRecipeDetail.strArea,
//                strInstructions = updatedRecipeDetail.strInstructions,
//                strMealThumb = updatedRecipeDetail.strMealThumb,
//                strIngredients = updatedRecipeDetail.ingredients,
//                isSaved = updatedRecipeDetail.isSaved
//            )
//        }
//    }
    fun clickSave(recipe: RecipeDetailsUiState) {
        viewModelScope.launch {
            try {
                _uiState.value = UIState.Loading // Setting the state to loading before updating
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
                _uiState.value = UIState.Error(e) // Handling potential errors during the update
            }
        }
    }

}