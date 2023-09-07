package com.example.yummyapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipesResponse(
    val meals: List<Meal>
)
@Serializable
data class TransformedRecipesResponse(
    val meals: List<TransformedMeal>
)
fun TransformedRecipesResponse.getFirstMeal(): TransformedMeal? {
    return meals.firstOrNull()
}