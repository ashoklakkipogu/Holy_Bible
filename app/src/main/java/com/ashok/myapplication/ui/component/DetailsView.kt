package com.ashok.myapplication.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.lyric.component.YouTubePlayer
import com.ashok.myapplication.ui.theme.BibleTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

@Composable
fun DetailView(
    youtubeId: String? = null,
    image: String? = null,
    description: String? = null,
    title: String? = null
) {
    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                if (image != null)
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        painter = painterResource(id = R.drawable.banner_1),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                    )
                if (youtubeId != null)
                    YouTubePlayer(
                        youtubeVideoId = youtubeId,
                        lifecycleOwner = LocalLifecycleOwner.current
                    )
                Column(
                    modifier = Modifier.padding(16.dp)
                        .verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false)
                ) {
                    title?.let {
                        Text(
                            text = it,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)
                        )
                    }

                    description?.let {
                        HtmlText(text = it, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailViewPreview() {
    DetailView()
}
