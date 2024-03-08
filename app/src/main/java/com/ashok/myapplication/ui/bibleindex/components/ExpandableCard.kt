package com.ashok.myapplication.ui.bibleindex.components

import android.R
import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpandableCard(
    data: BibleIndexModelEntry,
    onClick: (BibleIndexModelEntry) -> Unit,
    onClickIndex: (Int, Int) -> Unit,
    onSoundClick: (String) -> Unit
) {
    val expandedState = data.isExpand
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                onClick.invoke(data)
            }
        ) {
            Row(
                modifier = Modifier.weight(6f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = data.chapter,
                    fontSize = 16.sp,
                    fontWeight = if (expandedState) FontWeight.Bold else FontWeight.Normal
                )
                Spacer(modifier = Modifier.width(5.dp))
                IconButton(
                    modifier = Modifier
                        .size(25.dp),
                    onClick = {
                        onSoundClick.invoke(data.chapter)
                    },
                ) {
                    Icon(
                        painterResource(
                            R.drawable.ic_lock_silent_mode_off
                        ),
                        contentDescription = "Sound"
                    )
                }
            }

            IconButton(
                modifier = Modifier
                    .size(35.dp)
                    .alpha(0.5f)
                    .weight(1f)
                    .rotate(rotationState),
                onClick = {
                    onClick.invoke(data)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Drop Down Arrow"
                )
            }
        }
        if (expandedState) {
            val list: ArrayList<Int> = arrayListOf()
            for (i in 1..data.bibleChapterCount) {
                list.add(i)
            }
            NonLazyVerticalGrid(
                columns = 6,
                data = list
            ) { item ->
                IndexView(title = item) {
                    onClickIndex.invoke(data.chapter_id, it)
                }
            }

        }
    }

}

@Preview
@Composable
fun ExpandableCardPreview() {
    val model = BibleIndexModelEntry()
    model.chapter = "Test"
    ExpandableCard(
        data = model,
        onClick = {},
        onClickIndex = { _, _ ->
        },
        {}
    )
}


