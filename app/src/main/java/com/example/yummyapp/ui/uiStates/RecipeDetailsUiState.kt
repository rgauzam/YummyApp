package com.example.yummyapp.ui.uiStates

import com.example.yummyapp.data.model.Ingredient

data class RecipeDetailsUiState(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: String,
    val strIngredients: List<Ingredient>
)

val RecipeDetailsUiState.instructionList: List<String>
    get() = strInstructions.split("\n")

val RecipeDetailsUiState.tagList: List<String>
    get() = listOf(strCategory, strArea)