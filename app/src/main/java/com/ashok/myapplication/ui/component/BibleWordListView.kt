package com.ashok.myapplication.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleWordListView(
    title: String,
    subTitle: String,
    dividerColor: Color = Color.Black,
    heading: String = "",
    timings: String = "",
    isVisibleHeading: Boolean = false,
    onClick: () -> Unit
) {

    Card(
        onClick = {
            onClick.invoke()
        },
        modifier = Modifier
            .padding(5.dp),
        shape = RoundedCornerShape(2.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(5.dp)
        ) {
            Divider(
                color = dividerColor,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (isVisibleHeading) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = heading,
                                color = Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier.weight(1f),
                                overflow = TextOverflow.Ellipsis

                            )
                            Text(
                                text = timings,
                                color = Color.Gray,
                                fontSize = 10.sp,
                                textAlign = TextAlign.End
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Text(
                        text = title,
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = subTitle,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


            }
        }
    }
}

@Preview
@Composable
fun BibleListViewPreview() {
    BibleWordListView(
        title = "ఆదియందు దేవుడు భూమ్యాకాశములను సృజించెను. భూమి నిరాకారముగాను శూన్యముగాను ఉండెను; చీకటి అగాధ జలము పైన కమ్మియుండెను",
        subTitle = "ఆదియందు 1:2",
        dividerColor = Color.Black,
        heading = "You added a Note",
        timings = "2 weeks ago",
        isVisibleHeading = true
    ) {

    }
}