package com.example.submissioncompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.PlayerRepo
import com.example.submissioncompose.model.Player
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DetailViewModel(
    private val repository: PlayerRepo
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Player>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Player>>
        get() = _uiState

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean>
        get() = _isFavorite


    fun getPlayerById(playerId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getPlayerById(playerId))
        }
    }

    fun updateFavorite(playerId: Long, newState: Boolean) = viewModelScope.launch {
        repository.updatePlayerFavoriteStatus(playerId, !newState).collect { isUpdated ->
            if (isUpdated) getPlayerById(playerId)
        }
    }
}

