package com.ashok.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.ui.screens.MainScreen
import com.ashok.myapplication.ui.screens.Screens
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.viewmodel.HomeViewModel
import com.ashok.myapplication.ui.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()
    private val usersViewModel: UsersViewModel by viewModels()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        //viewModel.getAllProducts()
        //usersViewModel.getUserList(1)
        //viewModel.getBibleData()



        /*lifecycleScope.launch {
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
        }*/

       /* usersViewModel.userData.observe(this, Observer { state ->
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
        })*/

        setContent {
            BibleTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val currentRoute = currentDestination?.route ?: Screens.DashboardRoute.router
                MainScreen(navController, currentRoute, viewModel)

            }
        }
    }
}


