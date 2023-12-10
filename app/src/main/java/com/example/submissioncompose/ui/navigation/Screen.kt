package com.example.submissioncompose.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object FavoritePlayer : Screen("favoriteplayer")
    object Profile : Screen("profile")
    object DetailPlayer : Screen("home/{playerId}") {
        fun createRoute(playerId: Long) = "home/$playerId"
    }
}