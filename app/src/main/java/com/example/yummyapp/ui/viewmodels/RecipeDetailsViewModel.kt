package com.example.yummyapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yummyapp.data.model.TransformedMeal
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
    private val recipesRepository: RecipesRepository
) :
    ViewModel() {

    private val mealId: String = checkNotNull(savedStateHandle[IMAGE_DETAILS_ID_PARAM]).toString()
    private val _uiState: MutableStateFlow<RecipeDetailsUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<RecipeDetailsUiState?> = _uiState

    init {
        val idToFetch = mealId
        CoroutineScope(Dispatchers.Default).launch {
            val recipeDetail = recipesRepository.getRecipeDetails(idToFetch)
            _uiState.value = RecipeDetailsUiState(
                idMeal = recipeDetail.idMeal,
                strMeal = recipeDetail.strMeal,
                strCategory = recipeDetail.strCategory,
                strArea = recipeDetail.strArea,
                strInstructions = recipeDetail.strInstructions,
                strMealThumb = recipeDetail.strMealThumb,
                strIngredients = recipeDetail.ingredients,
                isSaved = recipeDetail.isSaved
            )
        }
    }

    fun saveRecipe(recipe: RecipeDetailsUiState) {
        CoroutineScope(Dispatchers.Default).launch {
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
            recipesRepository.insertRecipe(transformedMeal)

        }
    }

    fun removeRecipe(recipe: RecipeDetailsUiState) {
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
            recipesRepository.deleteRecipe(transformedMeal)
        }
    }



    fun clickSave(recipe: RecipeDetailsUiState) {
        CoroutineScope(Dispatchers.Default).launch {
            val updatedRecipeDetail = recipesRepository.updateUiState(recipe.idMeal)
            _uiState.value = RecipeDetailsUiState(
                idMeal = updatedRecipeDetail.idMeal,
                strMeal = updatedRecipeDetail.strMeal,
                strCategory = updatedRecipeDetail.strCategory,
                strArea = updatedRecipeDetail.strArea,
                strInstructions = updatedRecipeDetail.strInstructions,
                strMealThumb = updatedRecipeDetail.strMealThumb,
                strIngredients = updatedRecipeDetail.ingredients,
                isSaved = updatedRecipeDetail.isSaved
            )
        }

    }

    fun getLastId(): String {
        return recipesRepository.getLastIdDetails()
    }

}


//    init {
//        CoroutineScope(Dispatchers.Default).launch {
//            runCatching {
//                recipesRepository.getRecipeDetails(mealId)
//            }.onSuccess { meal ->
//                updateUiState(meal)
//            }.getOrElse { _ ->
//                runCatching {
//                    imagesRepository.getRecipeDetails(mealId)
//                }.onSuccess { meal ->
//                    updateUiState(meal)
//                }.getOrElse { e ->
//                    throw e
//                    //wyjebac i zrobic to w repo jak gadalismy i jest w notkach todo
//                }
//            }
//        }
//    }