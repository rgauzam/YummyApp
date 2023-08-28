package com.example.yummyapp.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class LocalMealWithIngredients(
    @Embedded
    val localMeal: LocalMeal,

    @Relation(parentColumn = "idMeal", entityColumn = "idMeal", entity = LocalIngredient::class)
    val ingredients: List<LocalIngredient>
)
