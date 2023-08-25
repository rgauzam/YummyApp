package com.example.yummyapp.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.yummyapp.R
import com.example.yummyapp.ui.components.RecipeItem
import com.example.yummyapp.ui.components.Recipes
import com.example.yummyapp.ui.navigation.Nav.IMAGE_DETAILS_SCREEN_ROUTE
import com.example.yummyapp.ui.uiStates.RecipeItemUiState
import com.example.yummyapp.ui.uiStates.UIState
import com.example.yummyapp.ui.uiStates.fakeRecipe
import com.example.yummyapp.ui.viewmodels.SearchRecipesViewModel

@Composable
fun SearchRecipesScreen(viewModel: SearchRecipesViewModel, navHostController: NavHostController) {
    val state = viewModel.uiState.collectAsState().value // łapie wszystko z view modelu
    val listState = state.uiState

    Column {
        SearchBar(
            modifier = Modifier,
            viewModel = viewModel,
            onSearch = { viewModel.loadImages() }
        )
        when (listState) {
            is UIState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UIState.Success -> {
                Recipes(listState.data, navHostController)
            }

            is UIState.Error -> {
                val error = (listState).exception
                Text(text = "Error: ${error.message}")
            }
        }
    }

}

@Composable
fun SearchBar(
    viewModel: SearchRecipesViewModel,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {},
) {
    var textFieldValue = viewModel.uiState.collectAsState().value

    OutlinedTextField(
        value = textFieldValue.searchingText,
        onValueChange = {
            viewModel.updateState(viewModel.uiState.value.copy(it))
        },
        keyboardActions = KeyboardActions(
            onDone = { onSearch(textFieldValue.searchingText) }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        shape = TextFieldDefaults.shape,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_extra_small))
    )
}

@Preview
@Composable
fun RecipeCardPreview() {
    RecipeItem(recipe = fakeRecipe, navHostController = rememberNavController())
}
