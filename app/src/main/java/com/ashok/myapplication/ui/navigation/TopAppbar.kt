package com.ashok.myapplication.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashok.myapplication.ui.screens.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(modifier: Modifier = Modifier, navigationState: DrawerState, currentRoute: String) {
    val scope = rememberCoroutineScope()
    CenterAlignedTopAppBar(title = {
        Text(text = currentRoute)
    }, navigationIcon = {
        IconButton(onClick = {
            scope.launch {
                navigationState.open()
            }
        }) {
            Icon(
                imageVector = Icons.Default.Menu, contentDescription = "Menu Icon"
            )
        }
    }, actions = {
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite icon"
            )
        }
    },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        modifier = modifier.shadow(elevation = 10.dp)

    )

}

@Preview
@Composable
fun topAppBarPreview() {
    topAppBar(navigationState = rememberDrawerState(initialValue = DrawerValue.Closed), currentRoute = Screens.DashboardRoute.router)
}