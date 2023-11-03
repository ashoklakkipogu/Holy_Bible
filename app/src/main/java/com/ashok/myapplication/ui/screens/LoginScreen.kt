package com.ashok.myapplication.ui.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.MainActivity
import com.ashok.myapplication.ui.theme.MyApplicationTheme

@Composable
fun LoginScreen(navController: NavController, modifier: Modifier = Modifier) {
    val activity = (LocalContext.current as? Activity)

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )  {

                Text(
                    "Login Screen",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 30.sp
                )
                Spacer(modifier.height(20.dp))
                Button(onClick = {
                    navController.navigate(Screens.Registration.router)
                }) {
                    Text(text = "Register page")
                }
                Spacer(modifier.height(20.dp))
                Button(onClick = {
                    navController.navigate(Screens.ForgotPassword.router)
                }) {
                    Text(text = "Forgot Password")
                }
                Button(onClick = {
                    val intent = Intent(activity, MainActivity::class.java)
                    activity?.startActivity(intent)
                    activity?.finish()
                }) {
                    Text(text = "Dashboard")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenView(){
    LoginScreen(rememberNavController())
}