package com.example.yummyapp.ui.uiStates

data class RecipeItemUiState(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
)


val fakeRecipe = RecipeItemUiState(
    idMeal = "ic_launcher.png",
    strMeal = "Fake Pancakes",
    strMealThumb = "https://example.com/fake_image.jpg"
)
//val ImageItemUiState.tagList: List<String>
//    get() = tags.split(",")
