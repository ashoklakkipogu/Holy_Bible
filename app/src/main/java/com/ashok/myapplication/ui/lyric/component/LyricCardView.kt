package com.ashok.myapplication.ui.lyric.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.myapplication.data.local.entity.LyricsModel
import com.ashok.myapplication.ui.utilities.RandomColors

@Composable
fun LyricCardView(
    title: String,
    onClick:() ->Unit
) {
    Column {
        ListItem(
            modifier = Modifier.clickable {
                onClick.invoke()
            },
            colors = ListItemDefaults.colors(
                containerColor = Color.White
            ),
            headlineContent = { Text(text = title) },
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(Color(RandomColors().color)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = title.substring(0, 2),
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            })
        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .padding(start = 10.dp, end = 10.dp),
            color = Color.LightGray
        )
    }

}


@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun LyricCardViewPreview() {
    LyricCardView(
        title = "Title"
    ){}
}