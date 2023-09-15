package com.example.yummyapp.di

import com.example.yummyapp.data.dataSoruce.RecipeLocalDataSource
import com.example.yummyapp.data.dataSoruce.RecipeRemoteDataSource
import com.example.yummyapp.data.repository.RecipesRepository
import com.example.yummyapp.data.repository.RecipesRepositoryI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestRepositoryModule {

    @Singleton
    @Provides
    fun provideRecipesRepository(
        remoteDataSource: RecipeRemoteDataSource,
        localDataSource: RecipeLocalDataSource
    ): RecipesRepositoryI {
        return RecipesRepository(remoteDataSource, localDataSource)
    }
}