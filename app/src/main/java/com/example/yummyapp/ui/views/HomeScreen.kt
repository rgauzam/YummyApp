package com.example.yummyapp.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.yummyapp.R

@Composable
fun HomeScreen() {
    Column() {
        SearchBar(//onSearch =  ->viewModel.updateState(viewModel.uiState.value.copy(searchingText = searchText))viewModel.loadImages())
        )
    }
}

// czy  nie powinno się robić tego tak żeby nie przekazywać tego tutaj tylko w tym końcowym
@Composable
fun SearchBar(
    hint: String = stringResource(R.string.search_label),
    onSearch: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {

}