package com.ashok.bible.ui.navgraph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.bible.data.local.entry.BibleModelEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    headingData: MutableState<BibleModelEntry>,
    leftArrowClick: () -> Unit,
    rightArrowClick: () -> Unit,
    verseClick: (BibleModelEntry) -> Unit
) {
    when (currentRoute) {
        Route.Bible.router -> Box(
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
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Left Icon"
                        )
                    }
                    Spacer(modifier.width(20.dp))
                    TextButton(
                        onClick = {
                            verseClick.invoke(headingData.value)
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
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Right Icon"
                        )
                    }
                }

            }
        }
        Route.Lyrics.router -> TopAppBar(title = { Text(text = "Lyrics") })
        Route.Discovery.router -> TopAppBar(title = { Text(text = "Discovery") })
    }
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
        currentRoute = Route.DashboardRoute.router,
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
    verseClick: (BibleModelEntry) -> Unit
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(start = 50.dp, top = 6.dp, end = 50.dp, bottom = 6.dp),
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
                    modifier.height(35.dp)

                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Left Icon"
                    )
                }
                Spacer(modifier.width(10.dp))
                TextButton(
                    onClick = {
                        verseClick.invoke(headingData)
                    },
                    modifier.height(35.dp)
                            .weight(1f),
                    contentPadding = PaddingValues(),
                ) {
                    Text(
                        text = "${headingData.bibleIndex} ${headingData.Chapter}",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis

                    )
                }

                Spacer(modifier.width(10.dp))
                IconButton(
                    onClick = {
                        rightArrowClick.invoke()
                    },
                    modifier.height(35.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
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
        headingData = BibleModelEntry(bibleIndex = "Test Test Test Test Test Test Test Test", Chapter = 1),
        verseClick = { })
}
