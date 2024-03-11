package com.ashok.bible.ui.discovery

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.ashok.bible.ui.bibleindex.components.TopAppBarView
import com.ashok.bible.ui.component.DetailView
import com.ashok.bible.ui.discovery.model.ImageGrid

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