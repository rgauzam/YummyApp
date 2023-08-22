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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.yummyapp.R
import com.example.yummyapp.data.model.Ingredient
import com.example.yummyapp.ui.uiStates.RecipeDetailsUiState
import com.example.yummyapp.ui.uiStates.instructionList
import com.example.yummyapp.ui.uiStates.tagList
import com.example.yummyapp.ui.viewmodels.RecipeDetailsViewModel

@Composable
fun RecipeDetailsScreen(viewModel: RecipeDetailsViewModel) {
    val state = viewModel.uiState.collectAsState().value
    state ?: return
    ImageDetailsView(state)
}

@Composable
fun ImageDetailsView(state: RecipeDetailsUiState) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))) {
            item {
                Text(
                    text = state.strMeal,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small))
                )
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                ) {
                    AsyncImage(
                        model = state.strMealThumb,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TagList(state.tagList)
                Text(
                    text = stringResource(R.string.ingredients),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Ingredients(input = state.strIngredients)
                Text(
                    text = stringResource(R.string.instructions),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small))
                )
                RecipeSteps(state.instructionList)
            }
        }

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
            SuggestionChip(
                onClick = {},
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small)),
                shape = SuggestionChipDefaults.shape,
                colors = SuggestionChipDefaults.suggestionChipColors(),
                elevation = SuggestionChipDefaults.suggestionChipElevation(),
                border = SuggestionChipDefaults.suggestionChipBorder(),
                label = {
                    Text(
                        text = "$tag"
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
                text = "$step",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small))
            )
            Divider()
        }

    }
}
