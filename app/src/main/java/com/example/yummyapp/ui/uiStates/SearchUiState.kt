package com.example.yummyapp.ui.uiStates


data class SearchUiState(
    val searchingText: String = "",
    val uiState: UIState<List<RecipeItemUiState>>
)



