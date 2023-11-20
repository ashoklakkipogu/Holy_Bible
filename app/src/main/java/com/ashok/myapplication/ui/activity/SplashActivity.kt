package com.ashok.myapplication.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils.split
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import com.ashok.myapplication.MainActivity
import com.ashok.myapplication.R
import com.ashok.myapplication.data.entity.BibleJson
import com.ashok.myapplication.ui.component.OnBoardingComponent
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.utilities.BibleUtils.getJsonDataFromAsset
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.utilities.SharedPrefUtils
import com.ashok.myapplication.ui.viewmodel.HomeViewModel
import com.ashok.myapplication.ui.viewmodel.SplashViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.isFirstTime()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        viewModel.bibleInsert.observe(this) { state ->
            when (state) {
                is Result.Loading -> {
                    Log.d("userApi", "Loading.......")

                }

                is Result.Success -> {
                    Log.d("userApi", "success......." + state.data)
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }

                is Result.Error -> {
                    Log.d("userApi", "error......." + state.error)

                }
            }
        }

        setContent {
            BibleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OnBoardingComponent {
                        viewModel.insertBible(this, langauge = "Telugu")
                    }
                }
            }
        }
    }

}
