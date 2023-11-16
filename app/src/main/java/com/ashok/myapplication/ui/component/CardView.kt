package com.ashok.myapplication.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.myapplication.R

@Composable
fun CardView(
    title: String,
    time:String,
    image: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.width(120.dp)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(10))
                .fillMaxWidth()
                .height(130.dp),
            contentScale = ContentScale.FillBounds,
        )
        Text(text = time,
            color = Color.LightGray,
            fontSize = 10.sp
        )
        Text(
            text = title,
            color = Color.Black,
            maxLines = 2,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis
        )
    }

}

@Preview
@Composable
fun CardViewViewPreview() {
    CardView(title = "Search by Topic", time = "now", image = R.drawable.onboard) {

    }
}