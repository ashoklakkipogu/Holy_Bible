package com.ashok.bible.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ashok.bible.R
import com.ashok.bible.ui.dashboard.DashboardUiState

@Composable
fun GridImages(state: DashboardUiState, onClickImage: (String) -> Unit) {

    Column {
        Column(Modifier.padding(top = 10.dp)) {
            Text(text = "Create image", fontSize = 14.sp)
            Text(
                text = "Choose your background image", color = Color.Gray, fontSize = 14.sp
            )
        }

        if (state.statusImages != null)
            LazyVerticalGrid(
                columns = GridCells.Fixed(3)
            ) {
                items(state.statusImages) { item ->
                    val painter =
                        rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png")/*Image(
                    painter = painter,
                    contentDescription = "Images"
                )*/
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.image)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(id = R.drawable.place_holder),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                onClickImage.invoke(item.image)
                            }
                            .size(128.dp),
                        contentScale = ContentScale.Crop
                    )


                    /*Image(
                        painter = painterResource(item),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clickable {
                                onClickImage.invoke(item)
                            }
                            .size(128.dp)
                    )*/


                }
            }
    }

}

@Preview
@Composable
fun GridImagesPreview() {
    GridImages(state = DashboardUiState()) {}
}