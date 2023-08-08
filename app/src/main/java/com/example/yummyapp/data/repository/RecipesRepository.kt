package com.example.yummyapp.data.repository

import com.example.yummyapp.data.dataSoruce.RecipeRemoteDataSource
import com.example.yummyapp.data.model.RecipeItem
import com.example.yummyapp.data.model.RecipesResponse
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RecipesRepository @Inject constructor(private val imagesRemoteDataSource: RecipeRemoteDataSource) {

    private lateinit var searchImagesResponse : RecipesResponse
    suspend fun getImages(query: String): RecipesResponse {
        searchImagesResponse = imagesRemoteDataSource.getImages(query)
        return searchImagesResponse
    }
//
//    suspend fun getImageDetails(imageId: Int) : RecipeItem {
//        searchImagesResponse.hits.find {
//            it.id == imageId
//        }?.let {
//            return it
//        }
//
//        return imagesRemoteDataSource.getImageDetails(imageId).hits.get(0)
//    }

}