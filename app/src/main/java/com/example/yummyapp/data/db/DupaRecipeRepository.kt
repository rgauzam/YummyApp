//package com.example.yummyapp.data.db
//
//import com.example.yummyapp.data.dataSoruce.DupaLocalDataSource
//import com.example.yummyapp.data.dataSoruce.DupaRemoteDataSource
//import javax.inject.Inject
//import javax.inject.Singleton
//
//
//data class TestDupaOb(val id: String, val name: String)
//
//@Singleton
//class DupaRecipeRepository @Inject constructor(
//    private val dupaLocalDataSource: DupaLocalDataSource,
//    private val dupaRemoteDataSource: DupaRemoteDataSource,
//) {
//
//    fun getRecipe() : TestDupaOb {
//        val result = dupaLocalDataSource.getRecipe()
//        if(result == null) {
//            val result = dupaRemoteDataSource.getRecipe()
//            dupaLocalDataSource.insertRecipe(result)
//            return result
//        }
//    }
//
//    fun getLastDetails() : TestDupaOb? {
//        val lastId = dupaLocalDataSource.getLastId()
//    }
//
//}