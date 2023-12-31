package com.example.yummyapp.data.dataSoruce

import com.example.yummyapp.data.model.Ingredient
import com.example.yummyapp.data.model.Meal
import com.example.yummyapp.data.model.RecipesResponse
import com.example.yummyapp.data.model.TransformedMeal
import com.example.yummyapp.data.model.TransformedRecipesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject


class RecipeRemoteDataSource @Inject constructor(private val client: HttpClient) {

    private lateinit var transformedRecipesResponse: TransformedRecipesResponse
    suspend fun getRecipes(query: String): TransformedRecipesResponse {
        val recipesResponse: RecipesResponse = client.get {
            url("https://www.themealdb.com/api/json/v1/1/search.php?s=$query")
        }.body()

        transformedRecipesResponse = transformMealList(recipesResponse.meals)
        return transformedRecipesResponse
    }

    suspend fun getRecipesDetails(id: String): TransformedRecipesResponse {
        val recipesResponse: RecipesResponse = client.get {
            url("https://www.themealdb.com/api/json/v1/1/lookup.php?i=$id")
        }.body()

        transformedRecipesResponse = transformMealList(recipesResponse.meals)
        return transformedRecipesResponse
    }


    fun transformMealList(input: List<Meal>): TransformedRecipesResponse {
        val transformedMeals = input.map { meal ->
            val ingredients = mutableListOf<Ingredient>()
            for (i in 1..20) {
                val ingredientName = meal.getIngredient(i)
                val measure = meal.getMeasure(i)
                if (!ingredientName.isNullOrBlank() || !measure.isNullOrBlank()) {
                    ingredients.add(Ingredient(ingredientName, measure))
                }
            }
            TransformedMeal(
                meal.idMeal,
                meal.strMeal,
                meal.strCategory,
                meal.strArea,
                meal.strInstructions,
                meal.strMealThumb,
                ingredients,
                false
            )
        }

        return TransformedRecipesResponse(transformedMeals)
    }


    fun Meal.getIngredient(index: Int): String? {
        return when (index) {
            1 -> strIngredient1
            2 -> strIngredient2
            3 -> strIngredient3
            4 -> strIngredient4
            5 -> strIngredient5
            6 -> strIngredient6
            7 -> strIngredient7
            8 -> strIngredient8
            9 -> strIngredient9
            10 -> strIngredient10
            11 -> strIngredient11
            12 -> strIngredient12
            13 -> strIngredient13
            14 -> strIngredient14
            15 -> strIngredient15
            16 -> strIngredient16
            17 -> strIngredient17
            18 -> strIngredient18
            19 -> strIngredient19
            20 -> strIngredient20
            else -> null
        }
    }

    fun Meal.getMeasure(index: Int): String? {
        return when (index) {
            1 -> strMeasure1
            2 -> strMeasure2
            3 -> strMeasure3
            4 -> strMeasure4
            5 -> strMeasure5
            6 -> strMeasure6
            7 -> strMeasure7
            8 -> strMeasure8
            9 -> strMeasure9
            10 -> strMeasure10
            11 -> strMeasure11
            12 -> strMeasure12
            13 -> strMeasure13
            14 -> strMeasure14
            15 -> strMeasure15
            16 -> strMeasure16
            17 -> strMeasure17
            18 -> strMeasure18
            19 -> strMeasure19
            20 -> strMeasure20
            else -> null
        }
    }

}