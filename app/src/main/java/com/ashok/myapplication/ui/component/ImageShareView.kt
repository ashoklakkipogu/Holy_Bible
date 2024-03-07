package com.ashok.myapplication.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ashok.myapplication.R

@Composable
fun ImageShare(
    image:String
) {
    Box (

    ){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.place_holder),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(2))
                .aspectRatio(0.8f),
            contentScale = ContentScale.FillBounds
        )

        OutlinedButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .alpha(0.9f),
            onClick = { },
            border = BorderStroke(1.dp, colorResource(id = R.color.colorAccent)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)

        ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Phone Icon",
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    tint = colorResource(id = R.color.colorAccent)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text("Share", color = colorResource(id = R.color.colorAccent))

        }
    }

}

@Composable
@Preview
fun ImageSharePreview() {
    ImageShare("https://www.bible.com/w_next/image?url=https%3A%2F%2Fimageproxy.youversionapi.com%2Fhttps%3A%2F%2Fs3.amazonaws.com%2Fyvplans%2F16431%2F1280x720.jpg&w=1920&q=75")
}