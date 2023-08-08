package com.example.yummyapp.data.dataSoruce

import com.example.yummyapp.BuildConfig
import com.example.yummyapp.data.model.RecipesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import okhttp3.OkHttpClient
import javax.inject.Inject

class RecipeRemoteDataSource @Inject constructor(private val client: HttpClient) {

    private val baseUrl = "https://pixabay.com/api"
    private val apiKey = BuildConfig.API_KEY

    suspend fun getImages(query: String) : RecipesResponse {
        return client.get {
            url("https://tasty.p.rapidapi.com/recipes/list?from=0&size=20&tags=under_30_minutes")
        }.body()
    }

//    suspend fun getImageDetails(id: Int) : RecipesResponse {
//        return client.get {
//            url("$baseUrl/?key=$apiKey&id=$id&image_type=photo")
//        }.body()

//
//    val request = Request.Builder()
//        .url("https://tasty.p.rapidapi.com/recipes/list?from=0&size=20&tags=under_30_minutes")
//        .get()
//        .addHeader("X-RapidAPI-Key", "4e847f969fmshb6a090e4cca36bcp101ca4jsndfe5a69a6a0d")
//        .addHeader("X-RapidAPI-Host", "tasty.p.rapidapi.com")
//        .build()
//
//    val response = client.newCall(request).execute()

}