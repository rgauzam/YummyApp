package com.example.yummyapp.ui.uiStates

data class SavedRecipesUiState(
    val savedRecipes: List<RecipeItemUiState> = emptyList()
)
