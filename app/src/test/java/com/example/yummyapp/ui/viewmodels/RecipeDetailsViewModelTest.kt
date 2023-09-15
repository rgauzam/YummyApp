package com.example.yummyapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.example.yummyapp.data.model.Ingredient
import com.example.yummyapp.data.repository.FakeRecipesRepository
import com.example.yummyapp.ui.navigation.Nav.IMAGE_DETAILS_ID_PARAM
import com.example.yummyapp.ui.uiStates.RecipeDetailsUiState
import com.example.yummyapp.ui.uiStates.UIState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RecipeDetailsViewModelTest {

    private lateinit var viewModel: RecipeDetailsViewModel
    private lateinit var fakeRepository: FakeRecipesRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        // Mock the SavedStateHandle
        savedStateHandle = mockk<SavedStateHandle>(relaxed = true)
        every { savedStateHandle.get<String>(IMAGE_DETAILS_ID_PARAM) } returns "fake_id"

        fakeRepository = FakeRecipesRepository()
        viewModel = RecipeDetailsViewModel(savedStateHandle, fakeRepository)
    }

    @Test
    fun `search recipe returns correct results`() = runBlocking {
        val query = "Meal A"
        val result = fakeRepository.searchRecipes(query)
        val expectedMeals =
            fakeRepository.getRecipes().first().filter { it.strMeal.contains(query, true) }
        assertEquals(expectedMeals, result.meals)
    }

    @Test
    fun `fetchRecipeDetails fetches correct recipe details`() = runBlocking {
        val mealId = "0"
        viewModel.fetchRecipeDetails(mealId)
        val expectedState = RecipeDetailsUiState(
            idMeal = "0",
            strMeal = "Meal A",
            strCategory = "Category A",
            strArea = "Area A",
            strInstructions = "Instructions for Meal A",
            strMealThumb = "URL for Meal A",
            strIngredients = listOf(
                Ingredient("Salt", "1 tsp"),
                Ingredient("Pepper", "1/2 tsp"),
                Ingredient("Oil", "1 tbsp")
            ),
            isSaved = false
        )

        assertEquals(UIState.Success(expectedState), viewModel.uiState.value)
    }

    @Test
    fun `chooseId chooses correct id`() {
        val defaultId = "{id}"
        val chosenId = viewModel.chooseId(defaultId)
        val expectedId = fakeRepository.getLastIdDetails()
        assertEquals(expectedId, chosenId)
    }

    @Test
    fun `clickSave toggles the save state of the recipe`() = runBlocking {
        val initialRecipe = RecipeDetailsUiState(
            idMeal = "0",
            strMeal = "Meal A",
            strCategory = "Category A",
            strArea = "Area A",
            strInstructions = "Instructions for Meal A",
            strMealThumb = "URL for Meal A",
            strIngredients = listOf(
                Ingredient("Salt", "1 tsp"),
                Ingredient("Pepper", "1/2 tsp"),
                Ingredient("Oil", "1 tbsp")
            ),
            isSaved = false
        )

        viewModel.clickSave(initialRecipe)

        val updatedState = (viewModel.uiState.value as? UIState.Success)?.data
        assertEquals(true, updatedState?.isSaved)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}