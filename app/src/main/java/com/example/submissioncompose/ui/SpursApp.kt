package com.example.submissioncompose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.submissioncompose.R
import com.example.submissioncompose.ui.navigation.NavigationItem
import com.example.submissioncompose.ui.navigation.Screen
import com.example.submissioncompose.ui.screen.detail.DetailScreen
import com.example.submissioncompose.ui.screen.favoriteplayer.FavoritePlayerScreen
import com.example.submissioncompose.ui.screen.home.HomeScreen
import com.example.submissioncompose.ui.screen.profile.ProfileScreen

@Composable
fun SpursApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailPlayer.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { playerId ->
                        navController.navigate(Screen.DetailPlayer.createRoute(playerId))
                    }
                )
            }
            composable(Screen.FavoritePlayer.route) {
                FavoritePlayerScreen(
                    navigateToDetail = { playerId ->
                    navController.navigate(Screen.DetailPlayer.createRoute(playerId))
                })
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
            composable(
                route = Screen.DetailPlayer.route,
                arguments = listOf(navArgument("playerId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("playerId") ?: -1L
                DetailScreen(
                    playerId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },

                    )
            }

        }
    }

}


@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favoriteplayer),
                icon = Icons.Default.Favorite,
                screen = Screen.FavoritePlayer
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.Person,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}

