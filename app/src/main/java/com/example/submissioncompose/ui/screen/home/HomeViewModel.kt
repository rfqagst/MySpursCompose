package com.example.submissioncompose.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.PlayerRepo
import com.example.submissioncompose.model.Player
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repository: PlayerRepo
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Player>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Player>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query


    fun getAllPlayer() {
        viewModelScope.launch {
            repository.getAllPlayer()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { player ->
                    _uiState.value = UiState.Success(player)
                }
        }
    }

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        try {
            val searchResult = repository.searchPlayers(_query.value)
            _uiState.value = UiState.Success(searchResult)
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message.toString())
        }
    }
}