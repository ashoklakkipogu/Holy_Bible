package com.ashok.bible.ui.activity

import android.content.Intent
import android.os.Bundle
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
import com.ashok.bible.MainActivity
import com.ashok.bible.ui.common.LoadingDialog
import com.ashok.bible.ui.component.OnBoardingComponent
import com.ashok.bible.ui.onboarding.OnboardingUIEvent
import com.ashok.bible.ui.theme.BibleTheme
import com.ashok.bible.ui.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModel.isFirstTime()) {
            callMainActivity()
        }

        setContent {
            BibleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val event = viewModel::onEvent
                    val state = viewModel.state
                    if (viewModel.state.isBibleInserted) {
                        callMainActivity()
                    }
                    //event(OnboardingUIEvent.OnEventIsFirstTime)
                    var showDialog by remember { mutableStateOf(false) }
                    if (state.isLoading) {
                        showDialog = true
                    }

                    if (showDialog) {
                        LoadingDialog {
                            showDialog = false
                        }
                    }
                    OnBoardingComponent { userName, langauge ->
                        viewModel.saveUserName(userName)
                        event(OnboardingUIEvent.InsertBible(langauge = langauge))
                    }
                }
            }
        }
    }

    private fun callMainActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

}
