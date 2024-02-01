package com.ashok.myapplication.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.myapplication.R
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.ui.screens.Screens
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    headingData: MutableState<BibleModelEntry>,
    leftArrowClick: () -> Unit,
    rightArrowClick: () -> Unit,
    verseClick: () -> Unit
) {
    if (currentRoute == Screens.Bible.router)
        Box(
            modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {

            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Row(
                    modifier = modifier.padding(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            leftArrowClick.invoke()
                        },
                        modifier.height(35.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Left Icon"
                        )
                    }
                    Spacer(modifier.width(20.dp))
                    TextButton(
                        onClick = {
                            verseClick.invoke()
                        },
                        modifier.height(35.dp),
                        contentPadding = PaddingValues(),
                    ) {
                        Text(
                            text = "${headingData.value.bibleIndex} ${headingData.value.Chapter}",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                        )
                    }

                    Spacer(modifier.width(20.dp))
                    IconButton(
                        onClick = {
                            rightArrowClick.invoke()
                        },
                        modifier.height(35.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Right Icon"
                        )
                    }
                }

            }
        }
    else if (currentRoute == Screens.Lyrics.router)
        TopAppBar(title = { Text(text = "Lyrics") })
    else if (currentRoute == Screens.Discovery.router)
        TopAppBar(title = { Text(text = "Discovery") })
    /*CenterAlignedTopAppBar(
        title = {
            Text(text = currentRoute)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        modifier = modifier.shadow(elevation = 10.dp)
    )*/

}

/*
@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun topAppBarPreview() {
    topAppBar(
        currentRoute = Screens.DashboardRoute.router,
        leftArrowClick = { },
        rightArrowClick = { },
        headingData = mutableStateOf(BibleModelEntry()),
        verseClick = { })
}*/

@Composable
fun HomeTopView(
    modifier: Modifier = Modifier,
    headingData: BibleModelEntry,
    leftArrowClick: () -> Unit,
    rightArrowClick: () -> Unit,
    verseClick: () -> Unit
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {

        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Row(
                modifier = modifier.padding(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        leftArrowClick.invoke()
                    },
                    modifier.height(35.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Left Icon"
                    )
                }
                Spacer(modifier.width(20.dp))
                TextButton(
                    onClick = {
                        verseClick.invoke()
                    },
                    modifier.height(35.dp),
                    contentPadding = PaddingValues(),
                ) {
                    Text(
                        text = "${headingData.bibleIndex} ${headingData.Chapter}",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                    )
                }

                Spacer(modifier.width(20.dp))
                IconButton(
                    onClick = {
                        rightArrowClick.invoke()
                    },
                    modifier.height(35.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Right Icon"
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun topAppBarPreview() {
    HomeTopView(
        leftArrowClick = { },
        rightArrowClick = { },
        headingData = BibleModelEntry(),
        verseClick = { })
}
