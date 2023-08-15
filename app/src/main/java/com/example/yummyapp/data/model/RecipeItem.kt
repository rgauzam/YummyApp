package com.example.yummyapp.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: String,
    val strYoutube: String,
    // val strIngredients: List<Ingredient>
    //Moze po prostu zapytam sie o to luka pozniej???
)

@Serializable
data class Ingredient(
    val name: String,
    val measure: String
)


