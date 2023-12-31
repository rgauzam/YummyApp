package com.example.yummyapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.yummyapp.R
import com.example.yummyapp.data.model.Ingredient
import com.example.yummyapp.ui.components.ErrorBox
import com.example.yummyapp.ui.uiStates.RecipeDetailsUiState
import com.example.yummyapp.ui.uiStates.UIState
import com.example.yummyapp.ui.uiStates.instructionList
import com.example.yummyapp.ui.uiStates.tagList
import com.example.yummyapp.ui.viewmodels.RecipeDetailsViewModel

@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailsViewModel,
    navHostController: NavHostController,

    ) {
    val state = viewModel.uiState.collectAsState().value
    ImageDetailsView(state, navHostController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailsView(
    state: UIState<RecipeDetailsUiState>,
    navHostController: NavHostController,
    viewModel: RecipeDetailsViewModel,
) = when (state) {
    is UIState.Loading -> {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    is UIState.Success -> {
        val recipeDetails = state.data
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.saved_recipes),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navHostController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Localized description"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                viewModel.clickSave(recipeDetails)
                            }) {
                                Icon(
                                    imageVector = if (recipeDetails.isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    )
                }
            )
            Box(modifier = Modifier) {
                LazyColumn(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))) {
                    item {
                        Text(
                            text = recipeDetails.strMeal,
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small))
                        )
                        Box(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                        ) {
                            AsyncImage(
                                model = recipeDetails.strMealThumb,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row() {
                            TagList(recipeDetails.tagList)
                        }

                        Text(
                            text = stringResource(R.string.ingredients),
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Ingredients(input = recipeDetails.strIngredients)
                        Text(
                            text = stringResource(R.string.instructions),
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small))
                        )
                        RecipeSteps(recipeDetails.instructionList)
                    }
                }
            }
        }
    }

    is UIState.Error -> {
        when (state.exception) {
            is java.net.UnknownHostException -> {
                ErrorBox(errorMessage = stringResource(R.string.internet_error))
            }

            else -> {
                ErrorBox(errorMessage = stringResource(R.string.no_recipes_error))
            }
        }
    }

    UIState.Idle -> {
        // Do nothing
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagList(tags: List<String>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        tags.forEach { tag ->

            AssistChip(
                onClick = {},
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small)),
                shape = SuggestionChipDefaults.shape,
                colors = SuggestionChipDefaults.suggestionChipColors(),
                elevation = SuggestionChipDefaults.suggestionChipElevation(),
                border = SuggestionChipDefaults.suggestionChipBorder(),
                label = {
                    Text(
                        text = tag
                    )
                },
            )
        }
    }
}


@Composable
fun Ingredients(input: List<Ingredient>) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        input.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_extra_small)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${item.strIngredient}",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${item.strMeasure}",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun RecipeSteps(steps: List<String>) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        steps.forEach { step ->
            Text(
                text = step,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small))
            )
            Divider()
        }
    }
}

