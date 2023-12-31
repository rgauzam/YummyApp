package com.example.yummyapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yummyapp.R
import com.example.yummyapp.ui.navigation.Nav.IMAGE_DETAILS_ID_PARAM
import com.example.yummyapp.ui.navigation.Nav.IMAGE_DETAILS_SCREEN_ROUTE
import com.example.yummyapp.ui.navigation.Nav.SAVED_IMAGES_SCREEN_ROUTE
import com.example.yummyapp.ui.navigation.Nav.SEARCH_IMAGES_SCREEN_ROUTE
import com.example.yummyapp.ui.theme.YummyAppTheme
import com.example.yummyapp.ui.viewmodels.RecipeDetailsViewModel
import com.example.yummyapp.ui.viewmodels.SavedRecipesViewModel
import com.example.yummyapp.ui.viewmodels.SearchRecipesViewModel
import com.example.yummyapp.ui.views.RecipeDetailsScreen
import com.example.yummyapp.ui.views.SavedRecipesScreen
import com.example.yummyapp.ui.views.SearchRecipesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YummyAppTheme {
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun YummyApp() {
    val navController = rememberNavController()
    val items = listOf(Screen.Search, Screen.Details, Screen.Saved)

    Scaffold(
        bottomBar = {
            NavigationBar() {
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(id = screen.label)) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    )
    { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = "$SEARCH_IMAGES_SCREEN_ROUTE"
            ) {
                composable(
                    "$SEARCH_IMAGES_SCREEN_ROUTE",
                ) {
                    val viewModel = hiltViewModel<SearchRecipesViewModel>()
                    SearchRecipesScreen(viewModel, navController)
                }
                composable(
                    IMAGE_DETAILS_SCREEN_ROUTE + "{$IMAGE_DETAILS_ID_PARAM}",
                    arguments = listOf(navArgument(IMAGE_DETAILS_ID_PARAM) {
                        type = NavType.StringType
                    })
                ) {
                    val viewModel = hiltViewModel<RecipeDetailsViewModel>()
                    RecipeDetailsScreen(viewModel, navController)
                }
                composable(
                    SAVED_IMAGES_SCREEN_ROUTE,
                ) {
                    val viewModel = hiltViewModel<SavedRecipesViewModel>()
                    SavedRecipesScreen(viewModel, navController)
                }
            }
        }
    }
}

sealed class Screen(val route: String, val label: Int, val icon: ImageVector) {
    object Search :
        Screen("$SEARCH_IMAGES_SCREEN_ROUTE", R.string.home, Icons.Filled.Home)

    object Details : Screen(
        IMAGE_DETAILS_SCREEN_ROUTE + "{$IMAGE_DETAILS_ID_PARAM}",
        R.string.recipe,
        Icons.Filled.Info
    )

    object Saved : Screen(SAVED_IMAGES_SCREEN_ROUTE, R.string.saved, Icons.Filled.Favorite)
}
