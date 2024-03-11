package com.ashok.bible.ui.component.share

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.bible.R

@Composable
fun TwoButtonView(title: String, onClick: (Int) -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .background(Color.LightGray, shape = RoundedCornerShape(5.dp))
                .padding(5.dp)
        ) {
            Icon(
                modifier = Modifier
                    .clickable {
                        onClick.invoke(1)
                    }
                    .size(30.dp)
                    .padding(3.dp),
                painter = painterResource(id = R.drawable.minus_icon),
                contentDescription = null
            )
            VerticalDivider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
            )

            Icon(
                modifier = Modifier
                    .clickable {
                        onClick.invoke(2)
                    }
                    .size(30.dp)
                    .padding(3.dp),
                painter = painterResource(id = R.drawable.plus_icon),
                contentDescription = null
            )
        }
        Text(
            text = title,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }


}

@Preview
@Composable
fun TwoButtonViewPreview() {
    TwoButtonView(
        title = "Text Align"
    ) {}
}