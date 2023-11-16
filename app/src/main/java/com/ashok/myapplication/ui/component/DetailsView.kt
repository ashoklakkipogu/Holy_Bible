package com.ashok.myapplication.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.theme.BibleTheme

@Composable
fun DetailView() {
    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    painter = painterResource(id = R.drawable.banner_1),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                )
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = "How To Study The Bible",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 5.dp, bottom = 10 .dp)
                    )
                    Text(
                        text = "It’s easy to feel overwhelmed, ill-equipped, and just plain lost when it comes to God's Word. My aim is to simplify the process of Bible Study for you in a few ways by teaching you three of the most important principles of successful Bible Study. Join this plan and discover how to read the Bible not just for information, but for life transformation today!",
                        fontSize = 16.sp
                    )
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
