package com.example.yummyapp.ui.views

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yummyapp.di.DatabaseModule
import com.example.yummyapp.ui.MainActivity
import com.example.yummyapp.ui.Screen
import com.example.yummyapp.ui.theme.YummyAppTheme
import com.example.yummyapp.ui.uiStates.SearchUiState
import com.example.yummyapp.ui.uiStates.UIState
import com.example.yummyapp.ui.viewmodels.SearchRecipesViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val viewModel: SearchRecipesViewModel = mockk(relaxed = true)

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            YummyAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Search.route
                ) {
                    composable(route = Screen.Search.route){
                        SearchRecipesScreen(viewModel = viewModel, navHostController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun testLoadingStateDisplayedOnSearch() {
        // Given a mocked viewModel with a loading state
        every { viewModel.uiState } returns MutableStateFlow(
            SearchUiState(uiState = UIState.Loading)
        )

        // Start the test by typing a query and triggering a search
        composeRule.onNodeWithContentDescription("searching").performTextInput("Chicken") // Replace "Search Bar Hint" with whatever hint or label your search bar might have.

        // Check if CircularProgressIndicator is displayed
        composeRule.onNodeWithTag("Loading indicator").assertIsDisplayed()
    }

}