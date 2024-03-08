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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ashok.myapplication.MainActivity
import com.ashok.myapplication.ui.common.LoadingDialog
import com.ashok.myapplication.ui.component.OnBoardingComponent
import com.ashok.myapplication.ui.onboarding.OnboardingUIEvent
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            BibleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val event = viewModel::onEvent
                    val state = viewModel.state
                    event(OnboardingUIEvent.OnEventIsFirstTime)
                    var showDialog by remember { mutableStateOf(false) }
                    if (state.isLoading) {
                        showDialog = true
                    }
                    if (state.isBibleInserted || state.isFirstTime) {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                    if (showDialog) {
                        LoadingDialog {
                            showDialog = false
                        }
                    }
                    OnBoardingComponent { userName, langauge ->
                        event(OnboardingUIEvent.InsertBible(langauge = langauge))
                    }
                }
            }
        }
    }

}
