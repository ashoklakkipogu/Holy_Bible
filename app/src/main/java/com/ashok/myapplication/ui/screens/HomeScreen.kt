package com.ashok.myapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.R
import com.ashok.myapplication.data.entity.Products
import com.ashok.myapplication.ui.component.bibleVerses
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.viewmodel.ProductsViewModel


@Composable
fun HomeScreen(
    productsViewModel: ProductsViewModel = hiltViewModel(),
    navController: NavController,
    scrollState: LazyListState
) {
    val productsRes by productsViewModel.products.collectAsState()
    productsViewModel.getAllProducts()
    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
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
                    products = response
                    Log.i("HomeScreen", "Success..............${response.data.toString()}")
                    bibleVerses(response.data, scrollState)
                }

                is Result.Error -> {
                    val error = (productsRes as Result.Error).error

                    Log.i("HomeScreen", "Error..............${error}")

                }
            }


        }
    }
}