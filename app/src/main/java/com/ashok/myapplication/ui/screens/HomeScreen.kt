package com.ashok.myapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ashok.myapplication.ui.theme.MyApplicationTheme
import com.ashok.myapplication.R
import com.ashok.myapplication.data.entity.Products
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.viewmodel.ProductsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun HomeScreen(
    productsViewModel: ProductsViewModel = hiltViewModel(),
    navController: NavController
) {
    val productsRes by productsViewModel.products.collectAsState()
    productsViewModel.getAllProducts()
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var products by remember {
                mutableStateOf(Products())
            }

            when (productsRes) {
                is Result.Loading -> {
                    Log.i("HomeScreen", "Loading..............")
                }

                is Result.Success -> {
                    val response = (productsRes as Result.Success).data
                    products= response
                    Log.i("HomeScreen", "Success..............${response.data.toString()}")

                }

                is Result.Error -> {
                    val error = (productsRes as Result.Error).error

                    Log.i("HomeScreen", "Error..............${error}")

                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .clip(MaterialTheme.shapes.large)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = "home_screen_bg",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = products.data.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
        }
    }
}