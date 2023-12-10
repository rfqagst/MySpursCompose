package com.example.submissioncompose.data

import com.example.submissioncompose.model.Player
import com.example.submissioncompose.model.PlayerData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PlayerRepo {
    private val player = mutableListOf<Player>()

    init {
        if (player.isEmpty()) {
            player.addAll(PlayerData.player)
        }
    }

    fun getAllPlayer(): Flow<List<Player>> {
        return flowOf(player)
    }

    fun getPlayerById(playerId: Long): Player {
        return player.first {
            it.id == playerId
        }
    }

    fun searchPlayers(query: String): List<Player>{
        return PlayerData.player.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getFavoritePlayers(): Flow<List<Player>> {
        return flowOf(player.filter { it.isFavorite })
    }

    fun updatePlayerFavoriteStatus(id: Long, newState: Boolean): Flow<Boolean> {
        val index = player.indexOfFirst { it.id == id }
        val result = if (index >= 0) {
            val playerItem = player[index]
            player[index] = playerItem.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }



    companion object {
        @Volatile
        private var instance: PlayerRepo? = null
        fun getInstance(): PlayerRepo =
            instance ?: synchronized(this) {
                PlayerRepo().apply {
                    instance = this
                }
            }
    }
}