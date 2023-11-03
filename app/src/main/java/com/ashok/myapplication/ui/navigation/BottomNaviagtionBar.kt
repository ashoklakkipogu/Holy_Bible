package com.ashok.myapplication.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ashok.myapplication.ui.screens.Screens

@Composable
fun bottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val items = listOf(
        Screens.Home,
        Screens.Lyric,
        Screens.Fav,
        Screens.Profile
    )
    var navSelectedItem by remember {
        mutableIntStateOf(0)
    }
    NavigationBar(
        modifier = Modifier.shadow(elevation = 10.dp)

    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.router } == true,
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        item.icon, contentDescription = item.title
                    )
                },
                onClick = {
                    //navSelectedItem = index
                    //navController.navigate(item.router)
                    navController.navigate(item.router) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}