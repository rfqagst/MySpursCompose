package com.example.submissioncompose.ui.screen.favoriteplayer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissioncompose.ViewModelFactory
import com.example.submissioncompose.di.Injection
import com.example.submissioncompose.model.Player
import com.example.submissioncompose.ui.common.UiState
import com.example.submissioncompose.ui.components.PlayerListItem

@Composable
fun FavoritePlayerScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {

    when (val uiState = viewModel.uiState.collectAsState(initial = UiState.Loading).value) {
        is UiState.Loading -> {
            viewModel.getFavoritePlayers()        }

        is UiState.Success -> {
            FavoritePlayersContent(players = uiState.data, navigateToDetail = navigateToDetail)
        }

        is UiState.Error -> {}
    }
}

@Composable
fun FavoritePlayersContent(players: List<Player>, navigateToDetail: (Long) -> Unit) {
    LazyColumn {
        items(players) { player ->
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
