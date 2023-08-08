package com.example.yummyapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipesResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<RecipeItem>
)