package com.example.submissioncompose.ui.screen.favoriteplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.PlayerRepo
import com.example.submissioncompose.model.Player
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: PlayerRepo
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Player>>> =
        MutableStateFlow(UiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    fun getFavoritePlayers() = viewModelScope.launch {
        repository.getFavoritePlayers()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

}