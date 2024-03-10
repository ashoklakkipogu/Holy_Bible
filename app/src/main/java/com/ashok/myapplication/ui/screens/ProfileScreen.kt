package com.ashok.myapplication.ui.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.MainActivity
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.common.LoadingDialog
import com.ashok.myapplication.ui.component.DialogLangaugeChange
import com.ashok.myapplication.ui.component.MenuItemView
import com.ashok.myapplication.ui.dashboard.DashboardUiState
import com.ashok.myapplication.ui.onboarding.OnboardingUIEvent
import com.ashok.myapplication.ui.onboarding.OnboardingUIState
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.utilities.BibleUtils.goToPlayStore
import com.ashok.myapplication.ui.utilities.BibleUtils.sendMail
import com.ashok.myapplication.ui.utilities.BibleUtils.shareApp
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(
    state: OnboardingUIState,
    homeState: DashboardUiState,
    event: (OnboardingUIEvent) -> Unit
) {
    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val context = LocalContext.current as Activity
            var isLoadingDialog by remember { mutableStateOf(false) }
            if (state.isLoading) {
                isLoadingDialog = true
            }
            if (state.isBibleInserted) {
                LaunchedEffect(state.isBibleInserted) {
                    context.startActivity(Intent(context, MainActivity::class.java))
                    context.finish()
                    Toast.makeText(context, "Successfully updated language.", Toast.LENGTH_SHORT)
                        .show()
                }
                isLoadingDialog = false
            }
            var isShowLanguageDialog by remember {
                mutableStateOf(false)
            }

            val gradient =
                Brush.horizontalGradient(listOf(colorResource(id = R.color.colorPrimaryDark), colorResource(id = R.color.colorPrimary)))

            if (isShowLanguageDialog) {
                DialogLangaugeChange(title = homeState.selectedLanguage, onDismiss = {
                    isShowLanguageDialog = false
                },
                    onSelectedLangauge = {
                        event(OnboardingUIEvent.InsertBible(langauge = it))
                    }
                )
            }
            if (isLoadingDialog) {
                LoadingDialog {
                    isLoadingDialog = false
                }
            }
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(gradient)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 50.dp, horizontal = 20.dp)
                    ) {
                        Text(
                            text = state.userName ?: stringResource(id = R.string.app_name),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        if (homeState.selectedLanguage.isNotEmpty())
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_note_24dp),
                                    contentDescription = "more",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .padding(end = 2.dp)
                                )
                                Text(
                                    text = homeState.selectedLanguage,
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }

                    }

                }
                Column(
                ) {
                    MenuItemView("Language Change", R.drawable.ic_language) {
                        isShowLanguageDialog = true
                    }
                    MenuItemView("Share App", R.drawable.ic_share) {
                        shareApp(context as Activity)
                    }
                    MenuItemView("Rate Us", R.drawable.ic_rate_us) {
                        goToPlayStore(context)
                    }
                    MenuItemView("Contact", R.drawable.ic_contact) {
                        sendMail(context)
                    }

                    MenuItemView(
                        "Request(Ideas, Lyrics, Stories, Status)",
                        R.drawable.lyrics_24
                    ) {
                        sendMail(context)
                    }
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun profileView() {
    ProfileScreen(OnboardingUIState(), DashboardUiState()) {}
}