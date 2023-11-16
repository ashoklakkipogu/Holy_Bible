package com.ashok.myapplication.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.myapplication.R

@Composable
fun MenuItemView(
    title: String,
    drawable: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick.invoke()
            }

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)

        ) {
            Icon(
                painter = painterResource(id = drawable),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(5.dp)
            )

        }
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 0.5.dp
        )

    }


}

@Preview
@Composable
fun MenuItemViewPreview() {
    MenuItemView("Profile", R.drawable.ic_language) {}
}