package com.example.yummyapp.ui.uiStates

import com.example.yummyapp.data.model.Ingredient
import com.example.yummyapp.data.model.LocalMeal

data class SavedRecipesUiState(
   // val savedRecipes: List<LocalMeal> = emptyList(),
    val savedRecipes: List<RecipeItemUiState> = emptyList()

//    val idMeal: String = "",
//    val strMeal: String= "",
//    val strCategory: String= "",
//    val strArea: String= "",
//    val strInstructions: String= "",
//    val strMealThumb: String= "",
  //  val ingredients: List<Ingredient>,
 //   val isAddingContact:Boolean = false,
//    val uiStateRecipes: List<RecipeItemUiState>?
) {
}