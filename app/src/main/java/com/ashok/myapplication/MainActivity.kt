package com.ashok.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.ui.navigation.bottomNavigation
import com.ashok.myapplication.ui.navigation.dashboardNavGraph
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
                        Log.d("productApi", "error......." + state.error)

                    }
                }
            }
        }

        usersViewModel.userData.observe(this, Observer { state ->
            when (state) {
                is Result.Loading -> {
                    Log.d("userApi", "Loading.......")

                }

                is Result.Success -> {
                    Log.d("userApi", "success......." + state.data.toString())
                }

                is Result.Error -> {
                    Log.d("userApi", "error......." + state.error)

                }
            }
        })

        setContent {
            BibleTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val currentRoute = currentDestination?.route ?: Screens.DashboardRoute.router
                MainScreen(navController, currentRoute)
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    currentRoute: String
) {
    val scorllState = rememberLazyListState()

    Scaffold(
        topBar = {
            topAppBar(
                currentRoute = currentRoute,
                leftArrowClick = {

                },
                rightArrowClick = {

                },
                verseClick = {

                }
            )
        },
        bottomBar = {
                bottomNavigation(navController, scorllState)

        },
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.DashboardRoute.router,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            dashboardNavGraph(navController, scorllState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BibleTheme {
        MainScreen(
            rememberNavController(),
            currentRoute = Screens.DashboardRoute.router
        )
    }
}


