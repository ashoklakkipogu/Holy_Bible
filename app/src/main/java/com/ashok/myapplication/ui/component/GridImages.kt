package com.ashok.myapplication.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ashok.myapplication.R

@Composable
fun GridImages() {
    val items = arrayListOf(R.drawable.onboard, R.drawable.banner_1, R.drawable.onboard, R.drawable.banner_1, R.drawable.banner_1, R.drawable.onboard, R.drawable.banner_1, R.drawable.banner_1, R.drawable.onboard, R.drawable.banner_1)
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(items) { item ->
            val painter =
                rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png")
            /*Image(
                painter = painter,
                contentDescription = "Images"
            )*/

            Image(
                painter = painterResource(item),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
            )


        }
    }
}

@Preview
@Composable
fun GridImagesPreview() {
    GridImages()
}