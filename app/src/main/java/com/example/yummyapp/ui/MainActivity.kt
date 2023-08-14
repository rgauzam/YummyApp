package com.example.yummyapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yummyapp.R
import com.example.yummyapp.ui.navigation.Nav.IMAGE_DETAILS_ID_PARAM
import com.example.yummyapp.ui.navigation.Nav.IMAGE_DETAILS_SCREEN_ROUTE
import com.example.yummyapp.ui.navigation.Nav.SEARCH_IMAGES_SCREEN_ROUTE
import com.example.yummyapp.ui.navigation.Nav.SEARCH_TEXT_PARAM
import com.example.yummyapp.ui.theme.YummyAppTheme
import com.example.yummyapp.ui.viewmodels.RecipeDetailsViewModel
import com.example.yummyapp.ui.viewmodels.SearchRecipesViewModel
import com.example.yummyapp.ui.views.RecipeDetailsScreen
import com.example.yummyapp.ui.views.SearchRecipesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YummyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    YummyApp()
                }
            }
        }
    }
}

@Composable

private fun YummyApp() {
    val navController = rememberNavController()
    val startSearchText = stringResource(R.string.start_search_text)
    NavHost(
        navController = navController,
        startDestination = "$SEARCH_IMAGES_SCREEN_ROUTE{$SEARCH_TEXT_PARAM}"
    ) {
        composable(
            "$SEARCH_IMAGES_SCREEN_ROUTE{$SEARCH_TEXT_PARAM}",
            arguments = listOf(navArgument(SEARCH_TEXT_PARAM) {
                type = NavType.StringType; defaultValue = startSearchText
            })
        ) {
            val viewModel = hiltViewModel<SearchRecipesViewModel>()
            SearchRecipesScreen(viewModel, navController)
        }

        composable(
            IMAGE_DETAILS_SCREEN_ROUTE + "{$IMAGE_DETAILS_ID_PARAM}",
            arguments = listOf(navArgument(IMAGE_DETAILS_ID_PARAM) { type = NavType.IntType })
        ) {
            val viewModel = hiltViewModel<RecipeDetailsViewModel>()
            RecipeDetailsScreen(viewModel)
        }
    }

}
