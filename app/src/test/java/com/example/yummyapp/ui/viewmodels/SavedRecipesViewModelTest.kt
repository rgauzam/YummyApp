package com.example.yummyapp.ui.viewmodels

import com.example.yummyapp.data.repository.FakeRecipesRepository
import com.example.yummyapp.ui.uiStates.RecipeItemUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SavedRecipesViewModelTest {
    private lateinit var viewModel: SavedRecipesViewModel
    private lateinit var fakeRepository: FakeRecipesRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        fakeRepository = FakeRecipesRepository()
        viewModel = SavedRecipesViewModel(fakeRepository)

    }

    @Test
    fun `loadRecipes loads the correct recipes`() = runBlocking {
        viewModel.loadRecipes()
        val expectedUiState = fakeRepository.getRecipes().first().map {
            RecipeItemUiState(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strMealThumb = it.strMealThumb
            )
        }
        assertEquals(expectedUiState, viewModel.uiState.value.savedRecipes)
    }

    @Test
    fun `getRecipes returns the correct recipes`() = runBlocking {
        val recipes = viewModel.getRecipes().first()
        val expectedRecipes = fakeRepository.getRecipes().first()
        assertEquals(expectedRecipes, recipes)
    }
}