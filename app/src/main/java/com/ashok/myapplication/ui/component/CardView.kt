package com.ashok.myapplication.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.discovery.model.ImageGrid
import com.ashok.myapplication.ui.utilities.RandomColors

@Composable
fun CardView(
    data: ImageGrid,
    onClickButton: (String?) -> Unit,
    onClickImage: (ImageGrid) -> Unit
) {
    if (data.image == null && data.drawable == null) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10))
                .size(120.dp)
                .background(data.color)
                .clickable {
                    onClickButton.invoke(data.title)
                }
        ) {
            if (data.title != null)
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(10.dp),
                    text = data.title!!,
                    color = Color.White,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
        }
    } else
        Column(
            modifier = Modifier
                .width(120.dp)
                .clickable {
                    onClickImage.invoke(data)
                }
        ) {
            Image(
                painter = if (data.image != null) rememberAsyncImagePainter(data.image) else painterResource(
                    id = data.drawable ?: R.drawable.banner_1
                ),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .fillMaxWidth()
                    .height(130.dp),
                contentScale = ContentScale.FillBounds,
            )
            /* Text(text = time,
                 color = Color.LightGray,
                 fontSize = 10.sp,
             )*/
            if (data.title != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = data.title!!,
                    color = Color.Black,
                    maxLines = 2,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }

}

@Preview
@Composable
fun CardViewViewPreview() {
    CardView(
        data = ImageGrid(title = "test"),
        onClickImage = {},
        onClickButton = {}
    )
}