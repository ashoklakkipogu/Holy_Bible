package com.ashok.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.ui.navigation.bottomNavigation
import com.ashok.myapplication.ui.navigation.dashboardNavGraph
import com.ashok.myapplication.ui.navigation.drawerContent
import com.ashok.myapplication.ui.navigation.topAppBar
import com.ashok.myapplication.ui.screens.Screens
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.viewmodel.ProductsViewModel
import com.ashok.myapplication.ui.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {

    private val viewModel: ProductsViewModel by viewModels()
    private val usersViewModel: UsersViewModel by viewModels()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        viewModel.getAllProducts()
        usersViewModel.getUserList(1)
        lifecycleScope.launch {
            viewModel.products.collect { state ->
                when (state) {
                    is Result.Loading -> {
                        Log.d("productApi", "Loading.......")
                    }

                    is Result.Success -> {
                        Log.d("productApi", "productApi......." + state.data.toString())
                    }

                    is Result.Error -> {
                        Log.d("productApi", "error......."+state.error)

                    }
                }
            }
        }

        usersViewModel.userData.observe(this, Observer { state->
            when(state){
                is Result.Loading ->{
                    Log.d("userApi", "Loading.......")

                }

                is Result.Success -> {
                    Log.d("userApi", "success......." + state.data.toString())
                }

                is Result.Error -> {
                    Log.d("userApi", "error......."+state.error)

                }
            }
        })

        setContent {
            BibleTheme {
                val navController = rememberNavController()
                val navigationState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val currentRoute = currentDestination?.route ?: Screens.DashboardRoute.router
                val scope = rememberCoroutineScope()
                val items = listOf(
                    Screens.Home, Screens.Lyric, Screens.Fav, Screens.Profile
                )
                ModalNavigationDrawer(
                    drawerContent = {
                        drawerContent(
                            items = items,
                            currentDestination = currentDestination,
                            onClickDrawerItem = { item ->
                                scope.launch {
                                    navigationState.close()
                                }
                                navController.navigate(item.router) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    },
                    drawerState = navigationState
                ) {
                    MainScreen(navController, navigationState, currentRoute)
                }

            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    navigationState: DrawerState,
    currentRoute: String
) {
    Scaffold(
        topBar = {
            topAppBar(
                navigationState = navigationState,
                currentRoute = currentRoute
            )
        },
        bottomBar = {
            bottomNavigation(navController)
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.DashboardRoute.router,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            dashboardNavGraph(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BibleTheme {
        MainScreen(
            rememberNavController(),
            rememberDrawerState(initialValue = DrawerValue.Open),
            currentRoute = Screens.DashboardRoute.router
        )
    }
}