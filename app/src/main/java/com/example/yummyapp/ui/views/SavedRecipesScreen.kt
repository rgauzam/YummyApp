package com.example.yummyapp.ui.views

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.example.yummyapp.R
import com.example.yummyapp.ui.components.Recipes
import com.example.yummyapp.ui.navigation.Nav
import com.example.yummyapp.ui.viewmodels.SavedRecipesViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedRecipesScreen(viewModel: SavedRecipesViewModel, navHostController: NavHostController) {
    val state = viewModel.uiState.collectAsState().value // łapie wszystko z view modelu

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    // val listState = state.uiState  to jest do pobiernaia z neta wiec tu sie nie przyda

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
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
                    IconButton(onClick = {navHostController.navigate(Nav.SEARCH_IMAGES_SCREEN_ROUTE + "cream") }) {
                        //DODAC TE NAWIGACJE DO TYŁU !!!
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ){
        Recipes(recipes =state.savedRecipes , navHostController = navHostController)

    }
}
//
//@Composable
//fun ContactScreen(
//    state: ContactState,
//    onEvent: (ContactEvent) -> Unit
//) {
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                onEvent(ContactEvent.ShowDialog)
//            }) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = "Add contact"
//                )
//            }
//        },
//    ) { _ ->
//        if (state.isAddingContact) {
//            AddContactDialog(state = state, onEvent = onEvent)
//        }
//
//        LazyColumn(
//            contentPadding = PaddingValues(16.dp),
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            item {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .horizontalScroll(rememberScrollState()),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    SortType.values().forEach { sortType ->
//                        Row(
//                            modifier = Modifier
//                                .clickable {
//                                    onEvent(ContactEvent.SortContacts(sortType))
//                                },
//                            verticalAlignment = CenterVertically
//                        ) {
//                            RadioButton(
//                                selected = state.sortType == sortType,
//                                onClick = {
//                                    onEvent(ContactEvent.SortContacts(sortType))
//                                }
//                            )
//                            Text(text = sortType.name)
//                        }
//                    }
//                }
//            }
//            items(state.contacts) { contact ->
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Column(
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(
//                            text = "${contact.firstName} ${contact.lastName}",
//                            fontSize = 20.sp
//                        )
//                        Text(text = contact.phoneNumber, fontSize = 12.sp)
//                    }
//                    IconButton(onClick = {
//                        onEvent(ContactEvent.DeleteContact(contact))
//                    }) {
//                        Icon(
//                            imageVector = Icons.Default.Delete,
//                            contentDescription = "Delete contact"
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//
//}