package com.example.submissioncompose.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissioncompose.ViewModelFactory
import com.example.submissioncompose.di.Injection
import com.example.submissioncompose.model.Player
import com.example.submissioncompose.ui.common.UiState
import com.example.submissioncompose.ui.components.MySearchBar
import com.example.submissioncompose.ui.components.PlayerListItem


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllPlayer()
            }

            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    player = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,

                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    player: List<Player>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,

    ) {
    Column {
        MySearchBar(
            query = query,
            onQueryChange = onQueryChange
        )
        LazyColumn {
            items(player) { player ->
                PlayerListItem(
                    name = player.name,
                    photoId = player.photoId,
                    modifier = Modifier.clickable {
                        navigateToDetail(player.id)
                    }
                )
            }
        }
    }
}

