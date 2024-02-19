package com.ashok.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ashok.myapplication.ui.screens.MainScreen
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.viewmodel.HomeViewModel
import com.ashok.myapplication.ui.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

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
                Log.i("test", "test.......BibleTheme")
                /*val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val currentRoute = currentDestination?.route ?: Route.DashboardRoute.router*/
                MainScreen(viewModel = viewModel)

            }
        }
    }
}


