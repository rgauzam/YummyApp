package com.example.yummyapp.data.dataSoruce

import com.example.yummyapp.data.model.RecipesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject


class RecipeRemoteDataSource @Inject constructor(private val client: HttpClient) {

//    private val baseUrl = "https://tasty.p.rapidapi.com/recipes/list?from=0&size=20"
//    private val apiKey = BuildConfig.API_KEY tutaj to raczej nie potrzebne bo apikey to 1

    suspend fun getRecipes(query: String): RecipesResponse {
        return client.get {
            url("https://www.themealdb.com/api/json/v1/1/search.php?s=$query")
        }.body()
    }

    suspend fun getRecipesDetails(id: String): RecipesResponse {
        return client.get {
            url("https://www.themealdb.com/api/json/v1/1/lookup.php?i=$id")
        }.body()
    }

}