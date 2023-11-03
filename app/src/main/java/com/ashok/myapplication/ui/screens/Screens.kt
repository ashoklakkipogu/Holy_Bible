package com.ashok.myapplication.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector
sealed class Screens(var router: String, var title: String, var icon: ImageVector) {
    object Home : Screens("home", "home", Icons.Default.Home)
    object Lyric : Screens("Lyric", "Lyric", Icons.Default.PlayArrow)
    object Fav : Screens("Fav", "Fav", Icons.Default.Favorite)
    object Profile : Screens("profile", "profile", Icons.Default.Person)

    object Login : Screens("login", "login", Icons.Default.ThumbUp)

    object Registration : Screens("registration", "registration", Icons.Default.ThumbUp)
    object ForgotPassword : Screens("forgotPassword", "forgotPassword", Icons.Default.ThumbUp)

    object DashboardRoute : Screens("dashboard", "dashboard", Icons.Default.ThumbUp)
    object AuthRoute : Screens("auth", "auth", Icons.Default.ThumbUp)



}