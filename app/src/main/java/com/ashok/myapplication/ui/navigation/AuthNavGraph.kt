package com.ashok.myapplication.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ashok.myapplication.ui.screens.Screens
import com.ashok.myapplication.ui.screens.ForgotPassword
import com.ashok.myapplication.ui.screens.LoginScreen
import com.ashok.myapplication.ui.screens.RegistrationScreen

fun NavGraphBuilder.authNavGraph(navController: NavController){
    navigation(startDestination = Screens.Login.router, route = Screens.AuthRoute.router){
        composable(Screens.Login.router) {
            LoginScreen(navController)
        }
        composable(Screens.Registration.router) {
            RegistrationScreen(navController)
        }
        composable(Screens.ForgotPassword.router) {
            ForgotPassword(navController)
        }
    }
}