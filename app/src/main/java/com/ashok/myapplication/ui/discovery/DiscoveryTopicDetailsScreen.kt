package com.ashok.myapplication.ui.discovery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ashok.myapplication.data.local.entity.QuotesModel
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView
import com.ashok.myapplication.ui.discovery.component.BibleWordCardView

@Composable
fun DiscoveryTopicDetailsScreen(title:String?, model: List<QuotesModel>?, onBackPress: () -> Unit) {
    Column {
        TopAppBarView(title?:"Discovery Details") {
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
                    BibleWordCardView(
                        heading = model.title,
                        title = model.quote,
                        des = model.author
                    )
                }
            }
    }

}