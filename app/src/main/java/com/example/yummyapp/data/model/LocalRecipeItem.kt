package com.example.yummyapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class LocalMeal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
   // val ingredients: List<Ingredient>,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
