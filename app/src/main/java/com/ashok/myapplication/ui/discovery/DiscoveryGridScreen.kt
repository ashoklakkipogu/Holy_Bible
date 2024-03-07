package com.ashok.myapplication.ui.discovery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView
import com.ashok.myapplication.ui.component.CardView
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.discovery.model.ImageGrid


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryGridScreen(
    list: List<ImageGrid>? = null,
    onBackPress: () -> Unit,
    onClickButton: (String?) -> Unit,
    onClickImage: (Int) -> Unit
) {
    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopAppBarView("Story/Topics") {
                    onBackPress.invoke()
                }
                if (list != null) {
                    LazyVerticalGrid(
                        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 10.dp),
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        itemsIndexed(list) { index, model ->
                            CardView(data = model, onClickButton = {
                                onClickButton.invoke(it)
                            }, onClickImage = {

                                onClickImage.invoke(index)
                            })
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun DiscoveryGridScreenPreview() {
    val image = listOf(ImageGrid("title", drawable = R.drawable.image_11))
    DiscoveryGridScreen(list = image, {}, {}, {})
}