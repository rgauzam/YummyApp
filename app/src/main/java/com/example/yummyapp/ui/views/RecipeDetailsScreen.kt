package com.example.yummyapp.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yummyapp.ui.uiStates.RecipeDetailsUiState
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
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
//                AsyncImage(
//                    model = state.bigUrl,
//                    contentDescription = "",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(4.dp))
//                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Nazwa: ${state.idMeal}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Nazwa: ${state.strMeal}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Liczba lajków: ${state.strCategory}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }
        }

    }
}