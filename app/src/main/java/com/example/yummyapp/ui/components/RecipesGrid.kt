package com.example.yummyapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.yummyapp.R
import com.example.yummyapp.ui.navigation.Nav
import com.example.yummyapp.ui.uiStates.RecipeItemUiState

@Composable
fun Recipes(
    recipes: List<RecipeItemUiState>,
    navHostController: NavHostController
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(135.dp),
        contentPadding = PaddingValues(4.dp),
        content = {
            items(recipes) { recipe ->
                RecipeItem(recipe, navHostController)
            }
        },
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
fun RecipeItem(
    recipe: RecipeItemUiState,
    navHostController: NavHostController
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.elevatedCardColors(),
        modifier = Modifier
            .padding(dimensionResource(R.dimen.card_spacer))
            .clickable {
                val recipeId = recipe.idMeal
                navHostController.navigate(Nav.IMAGE_DETAILS_SCREEN_ROUTE + recipeId)
            }
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small)),
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
            ) {
                AsyncImage(
                    model = recipe.strMealThumb,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
            Text(
                text = recipe.strMeal,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Start,
            )
        }
    }
}
