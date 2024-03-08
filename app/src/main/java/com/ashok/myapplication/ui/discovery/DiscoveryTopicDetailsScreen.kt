package com.ashok.myapplication.ui.discovery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.ashok.myapplication.data.local.entity.QuotesModel
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView
import com.ashok.myapplication.ui.dashboard.DashboardUiEvent
import com.ashok.myapplication.ui.discovery.component.BibleWordCardView

@Composable
fun DiscoveryTopicDetailsScreen(
    title: String?,
    model: List<QuotesModel>?,
    homeEvent: (DashboardUiEvent) -> Unit,
    onBackPress: () -> Unit
) {
    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }
    DisposableEffect(Unit) {
        println("DisposableEffect: entered main")
        onDispose {
            homeEvent(DashboardUiEvent.TextSpeechStop)
            println("DisposableEffect: exited main")
        }
    }

    Column {
        TopAppBarView(title ?: "Discovery Details") {
            onBackPress.invoke()
        }
        if (model != null)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentPadding = PaddingValues(10.dp)
            ) {
                itemsIndexed(model) { index, model ->
                    val isSelectedIndex = selectedIndex == index

                    BibleWordCardView(
                        heading = model.title,
                        title = model.quote,
                        des = model.author,
                        color = model.color,
                        isSelectedIndex = isSelectedIndex,
                        homeEvent = homeEvent
                    ) {
                        selectedIndex = if (selectedIndex == index) -1 else index
                    }
                }
            }
    }

}