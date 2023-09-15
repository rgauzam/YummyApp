package com.example.yummyapp.di

import android.content.Context
import androidx.room.Room
import com.example.yummyapp.data.db.RecipeDao
import com.example.yummyapp.data.db.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestDatabaseModule {

    @Singleton
    @Provides
    fun provideRecipeDatabase(@ApplicationContext context: Context): RecipeDatabase {
        return Room.inMemoryDatabaseBuilder(context, RecipeDatabase::class.java)
            .allowMainThreadQueries()  //mozna to usunac todo
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(database: RecipeDatabase): RecipeDao {
        return database.recipeDao()
    }
}
