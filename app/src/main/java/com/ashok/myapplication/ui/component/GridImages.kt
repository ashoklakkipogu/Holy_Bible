package com.ashok.myapplication.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ashok.myapplication.R

@Composable
fun GridImages(onClickImage: (Int) -> Unit) {
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
        Column(Modifier.padding(top = 10.dp)) {
            Text(text = "Create image", fontSize = 14.sp)
            Text(
                text = "Choose your background image", color = Color.Gray, fontSize = 14.sp
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            items(items) { item ->
                val painter =
                    rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png")/*Image(
                    painter = painter,
                    contentDescription = "Images"
                )*/

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


            }
        }
    }

}

@Preview
@Composable
fun GridImagesPreview() {
    GridImages(){}
}