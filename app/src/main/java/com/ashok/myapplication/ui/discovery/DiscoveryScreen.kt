package com.ashok.myapplication.ui.discovery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView
import com.ashok.myapplication.ui.component.ButtonView
import com.ashok.myapplication.ui.component.CardView
import com.ashok.myapplication.ui.component.TitleView
import com.ashok.myapplication.ui.component.colorView
import com.ashok.myapplication.ui.discovery.component.CardShimmer
import com.ashok.myapplication.ui.discovery.component.QuotesShimmer
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.utilities.RandomColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    state: DiscoveryUIState,
    onBackPress: () -> Unit
) {
    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopAppBarView("Discovery") {
                    onBackPress.invoke()
                }
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    if (state.quotesTitles.isNotEmpty()) {
                        TitleView(title = "Search by Topic", onClick = {})
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)

                        ) {
                            items(state.quotesTitles) { item ->
                                ButtonView(title = item, color = Color(RandomColors().color)) {}
                            }
                        }
                    }

                    if (state.isLoadingQuotes) {
                        QuotesShimmer()
                    }

                    if (state.storyList.isNotEmpty()){
                        TitleView(title = "New to Faith", onClick = {})
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)

                        ) {
                            items(state.storyList) { item ->
                                CardView(
                                    title = item.title,
                                    time = "12/12/2023",
                                    image = item.url
                                ) {

                                }
                            }

                        }
                    }

                    if (state.isLoadingStory){
                        CardShimmer()
                    }

                }
            }


        }
    }
}

@Preview
@Composable
fun DiscoveryScreenPreview() {
    DiscoveryScreen(state = DiscoveryUIState()) {}
}