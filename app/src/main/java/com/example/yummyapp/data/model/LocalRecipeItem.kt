package com.example.yummyapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class LocalMeal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Entity(tableName = "ingredients")
data class LocalIngredient(
    val idMeal: String,
    val strIngredient: String?,
    val strMeasure: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)