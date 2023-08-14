package com.example.yummyapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipesResponse(
    val meals: List<Meal>
)