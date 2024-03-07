package com.ashok.myapplication.ui.discovery

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView
import com.ashok.myapplication.ui.component.DetailView
import com.ashok.myapplication.ui.discovery.model.ImageGrid

@Composable
fun DiscoveryStoryDetailsScreen(title: String?, model: ImageGrid?, onBackPress: () -> Unit) {
    Column {
        TopAppBarView(title ?: "Discovery Details") {
            onBackPress.invoke()
        }
        DetailView(
            image = model?.image,
            description = model?.des,
            title = model?.title
        )
    }

}