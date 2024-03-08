package com.ashok.myapplication.ui.discovery.component

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.component.HtmlText
import com.ashok.myapplication.ui.dashboard.DashboardUiEvent
import com.ashok.myapplication.ui.dashboard.DashboardUiState
import com.ashok.myapplication.ui.utilities.BibleUtils
import com.ashok.myapplication.ui.utilities.BibleUtils.htmlToPlainText
import com.ashok.myapplication.ui.utilities.RandomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleWordCardView(
    heading: String? = null,
    title: String? = null,
    des: String? = null,
    color: Int,
    isSelectedIndex: Boolean,
    homeEvent: (DashboardUiEvent) -> Unit,
    onMicClick: () -> Unit
) {
    val context = LocalContext.current

    /*var micIconToggle by remember {
        mutableStateOf(false)
    }*/

    ElevatedCard(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(color)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(2.dp),
        onClick = {
            //onClickIndex.invoke(title)
        }
    ) {
        Column {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                if (heading != null) {
                    Text(
                        text = heading,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
                if (title != null)
                    HtmlText(
                        text = title,
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                if (des != null)
                    Text(
                        text = des,
                        fontSize = 12.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
            }
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp),
                color = Color.LightGray
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            BibleUtils.onClickShare(context, title, des)
                        },
                    painter = painterResource(R.drawable.ic_share),
                    contentDescription = "share",
                )
                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            BibleUtils.onClickCopy(context, title, des)
                        },
                    painter = painterResource(R.drawable.ic_copy),
                    contentDescription = "share",
                )
                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            onMicClick.invoke()
                            if (!isSelectedIndex)
                                homeEvent(
                                    DashboardUiEvent.TextSpeechPlay(
                                        htmlToPlainText("$title - \n $des")
                                    )
                                )
                            else
                                homeEvent(DashboardUiEvent.TextSpeechStop)
                        },
                    painter = if (isSelectedIndex) painterResource(R.drawable.ic_stop_icon) else painterResource(
                        android.R.drawable.ic_lock_silent_mode_off
                    ),
                    contentDescription = "share",
                )
            }

        }


    }
}

@Composable
@Preview
fun BibleWordCardViewPreview() {
    BibleWordCardView(
        heading = "Heading",
        title = "Title",
        des = "desc",
        color = RandomColors.color,
        isSelectedIndex = false,
        {}, {}
    )
}