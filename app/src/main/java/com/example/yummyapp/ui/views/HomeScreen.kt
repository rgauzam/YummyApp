package com.example.yummyapp.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.yummyapp.R
import com.example.yummyapp.ui.components.ErrorBox
import com.example.yummyapp.ui.components.Recipes
import com.example.yummyapp.ui.uiStates.SearchUiState
import com.example.yummyapp.ui.uiStates.UIState
import com.example.yummyapp.ui.viewmodels.SearchRecipesViewModel

@Composable
fun SearchRecipesScreen(viewModel: SearchRecipesViewModel, navHostController: NavHostController) {
    val state = viewModel.uiState.collectAsState().value
    val listState = state.uiState

    Column {
        SearchBar(
            modifier = Modifier,
            viewModel = viewModel,
            onSearch = { viewModel.loadImages() }
        )

        when (listState) {
            is UIState.Idle -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                    Text(
                        text = stringResource(R.string.welcome_message),
                        textAlign = TextAlign.Justify
                    )
                }
            }

            is UIState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                    CircularProgressIndicator(modifier = Modifier.testTag("Loading indicator"))

                }
            }

            is UIState.Success -> {
                Recipes(listState.data, navHostController)
            }

            is UIState.Error -> {
                when ((listState).exception) {
                    is java.net.UnknownHostException -> {
                        ErrorBox(errorMessage = stringResource(R.string.internet_error))
                    }

                    else -> {
                        ErrorBox(errorMessage = stringResource(R.string.no_recipes_error))
                    }
                }
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
    var textFieldValue by remember { mutableStateOf("") }

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
        },
        keyboardActions = KeyboardActions(
            onDone = {
                viewModel.updateState(
                    SearchUiState(
                        searchingText = textFieldValue,
                        uiState = UIState.Loading
                    )
                )
                onSearch(textFieldValue)
            }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "searching") },
        shape = TextFieldDefaults.shape,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_extra_small))
            //.testTag("searching")
    )
}


