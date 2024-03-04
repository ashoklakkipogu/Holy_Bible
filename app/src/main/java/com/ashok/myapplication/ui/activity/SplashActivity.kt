package com.ashok.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ashok.myapplication.MainActivity
import com.ashok.myapplication.ui.component.OnBoardingComponent
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

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
                    //Log.d("userApi", "error......." + state.error)

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
