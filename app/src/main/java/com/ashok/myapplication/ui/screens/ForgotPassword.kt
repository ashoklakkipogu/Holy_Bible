package com.ashok.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.ui.theme.BibleTheme

@Composable
fun ForgotPassword(navController: NavController, modifier: Modifier = Modifier) {
    BibleTheme {
        Surface(
            modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Forgot Password Page", fontSize = 30.sp)
                Spacer(modifier.height(10.dp))
                Button(onClick = {
                    navController.navigate(Screens.Registration.router)
                }) {
                    Text(text = "Registration")
                }
                Spacer(modifier.height(10.dp))
                Button(onClick = {
                    navController.navigate(Screens.Login.router){
                        popUpTo(Screens.Login.router){
                            inclusive = true
                        }
                    }
                }) {
                    Text(text = "Login")
                }
            }
        }
    }
}

@Preview
@Composable
fun ForgotPasswordView() {
    ForgotPassword(navController = rememberNavController())
}