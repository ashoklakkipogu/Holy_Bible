package com.ashok.myapplication.ui.discovery.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView

@Composable
fun DiscoveryDetailsGridView(
    onClickImage: (Int) -> Unit
) {
    val items = arrayListOf(
        R.drawable.onboard,
        R.drawable.banner_1,
        R.drawable.image_1,
        R.drawable.image_2,
        R.drawable.image_3,
        R.drawable.image_4,
        R.drawable.image_6,
        R.drawable.image_7,
        R.drawable.image_9,
        R.drawable.image_8,
        R.drawable.image_10,
        R.drawable.image_11,
        R.drawable.image_12,
        R.drawable.image_13
    )
    Column {
        TopAppBarView("Discovery") {
            //onBackPress.invoke()
        }
        LazyVerticalGrid(
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 10.dp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items) { item ->
                Column {
                    Image(
                        painter = painterResource(item),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clickable {
                                onClickImage.invoke(item)
                            }
                            .size(128.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "title",
                        color = Color.Black,
                        maxLines = 2,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }


            }
        }
    }

}

@Preview
@Composable
fun DiscoveryDetailsGridViewPreview() {
    DiscoveryDetailsGridView(){}
}