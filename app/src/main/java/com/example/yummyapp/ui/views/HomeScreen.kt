package com.example.yummyapp.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.yummyapp.R
import com.example.yummyapp.ui.navigation.Nav.IMAGE_DETAILS_SCREEN_ROUTE
import com.example.yummyapp.ui.uiStates.RecipeItemUiState
import com.example.yummyapp.ui.uiStates.UIState
import com.example.yummyapp.ui.viewmodels.SearchRecipesViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode


@Composable
fun SearchRecipesScreen(viewModel: SearchRecipesViewModel, navHostController: NavHostController) {
    val state = viewModel.uiState.collectAsState().value // łapie wszystko z view modelu
    val listState = state.uiState

    Column {
        SearchBar(modifier = Modifier
            .padding(5.dp),
            viewModel = viewModel, onSearch = {
                viewModel.loadImages()
            })
        when (listState) {
            is UIState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UIState.Success -> {
                RecipesList(listState.data, navHostController)
            }

            is UIState.Error -> {
                val error = (listState).exception
                Text(text = "Error: ${error.message}")
            }
        }
    }

}

@Composable
fun RecipesList(
    recipes: List<RecipeItemUiState>,
    navHostController: NavHostController
) {
    LazyColumn {
        items(recipes) { recipe ->
            ImageCard(recipe, navHostController)
        }
    }
}

@Composable
fun ImageCard(
    recipe: RecipeItemUiState,
    navHostController: NavHostController
) {
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = stringResource(R.string.details_next_dialog))
            },
            confirmButton = {
                Button(onClick = {
                    showDialog.value = false
                    val recipeId = recipe.idMeal
                    navHostController.navigate(IMAGE_DETAILS_SCREEN_ROUTE + recipeId)
                }) {
                    Text(text = stringResource(R.string.yes))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text(text = stringResource(R.string.no))
                }
            }
        )
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                showDialog.value = true
            }
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.Top
        ) {

//            AsyncImage(
//                model = recipe.url, //tutaj trzeba dodac zdjecie
//                contentDescription = "",
//                modifier = Modifier
//                    .size(125.dp)
//                    .clip(RoundedCornerShape(4.dp))
//            )
//
//            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = stringResource(R.string.user)+" "+recipe.strMeal,
                   // text = "Użytkownik: ${recipe.strMeal}",
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                )
                Text(text = stringResource(R.string.id)+" "+recipe.idMeal)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
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
        shape = MaterialTheme.shapes.small,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
    )
}
