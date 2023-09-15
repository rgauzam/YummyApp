package com.example.yummyapp.ui.viewmodels

import com.example.yummyapp.data.model.TransformedRecipesResponse
import com.example.yummyapp.data.repository.FakeRecipesRepository
import com.example.yummyapp.ui.uiStates.RecipeItemUiState
import com.example.yummyapp.ui.uiStates.SearchUiState
import com.example.yummyapp.ui.uiStates.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchRecipesViewModelTest {
    private lateinit var viewModel: SearchRecipesViewModel
    private lateinit var fakeRepository: FakeRecipesRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        fakeRepository = FakeRecipesRepository()
        viewModel = SearchRecipesViewModel(fakeRepository)

    }

    @Test
    fun `loadImages loads the correct images`() = runBlocking {
        // Given the fake data in the FakeRecipesRepository

        // When loadImages is called
        viewModel.loadImages()

        // Then the uiState should have the correct images
        val expectedUiState = fakeRepository.searchRecipes("").meals.map {
            RecipeItemUiState(it.idMeal, it.strMeal, it.strMealThumb)
        }
        assertEquals(UIState.Success(expectedUiState), viewModel.uiState.first().uiState)
    }

    @Test
    fun `updateState updates the uiState correctly`() {
        // Given a sample UIState
        val sampleState = SearchUiState(uiState = UIState.Loading)

        // When updateState is called
        viewModel.updateState(sampleState)

        // Then the viewModel's uiState should match the sampleState
        assertEquals(sampleState, viewModel.uiState.value)
    }

    @Test
    fun `searchRecipes returns the correct recipes`() = runBlocking {
        val query = "Meal A"
        val recipes = viewModel.searchRecipes(query)
        val expectedRecipes = fakeRepository.searchRecipes(query)
        assertEquals(expectedRecipes, recipes)
    }

    @Test
    fun `cr8UiStateFromResponse creates correct UIState`() {
        // Given a TransformedRecipesResponse from the fake data
        val response = TransformedRecipesResponse(fakeRepository.fakeData)

        // When cr8UiStateFromResponse is called
        val uiState = viewModel.cr8UiStateFromResponse(response)

        // Then the returned UIState should correctly map the response to RecipeItemUiState
        val expectedUiState = UIState.Success(fakeRepository.fakeData.map {
            RecipeItemUiState(it.idMeal, it.strMeal, it.strMealThumb)
        })
        assertEquals(expectedUiState, uiState)
    }
}