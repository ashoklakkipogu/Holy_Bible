package com.ashok.myapplication.ui.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.myapplication.R
import com.ashok.myapplication.data.local.entry.BibleModelEntry

@Composable
fun bibleVerses(
    response: List<BibleModelEntry>,
    scrollState: LazyListState,
    headingData: MutableState<BibleModelEntry>
) {
    val selectedIndex by remember { mutableStateOf(response) }
    val onItemClick =
        { index: Int, model: BibleModelEntry -> selectedIndex.get(index).isSelected = model.isSelected }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
        state = scrollState
    ) {
        val currentItemIndex = scrollState.firstVisibleItemIndex
        Log.i("currentItemIndex", "currentItemIndex...........${currentItemIndex}")
        headingData.value = response[currentItemIndex + 1]
        itemsIndexed(response) { index, model ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (model.Versecount == 1) {
                        BibleHeading(model)
                    }
                    BibleVerse(
                        model,
                        index = index,
                        selected = selectedIndex[index].isSelected == model.isSelected,
                        onClick = onItemClick
                    )
                }

            }
        }
    }
}

@Composable
fun BibleHeading(model: BibleModelEntry) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = model.bibleIndex,
            fontSize = 20.sp,
            lineHeight = 10.sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = model.Chapter.toString(),
            fontSize = 32.sp,
            lineHeight = 10.sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
fun BibleVerse(
    model: BibleModelEntry,
    index: Int,
    selected: Boolean,
    onClick: (Int, BibleModelEntry) -> Unit
) {
    Box(modifier = Modifier
        .clickable {
            onClick.invoke(index, model)
        }
        .fillMaxWidth()) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        baselineShift = BaselineShift.Superscript,
                        color = colorResource(id = R.color.colorAccent),
                        fontSize = 14.sp,

                        )
                ) {
                    append("${model.Versecount} ")
                }
                withStyle(
                    style = SpanStyle(
                        textDecoration = if (selected) TextDecoration.Underline else TextDecoration.None,
                        fontSize = 16.sp
                    )

                ) {
                    append("${model.verse} ")
                }

            },
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
        )
    }

}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun BibleVersesPreview() {
    val dataList = ArrayList<BibleModelEntry>()
    val data = BibleModelEntry()
    data.verse =
        "In the sweat of your face shall you eat bread, " + "till you return unto the ground; for out of it were you taken: " + "for dust you are, and unto dust shall you return."
    data.Versecount = 1
    dataList.add(data)
    bibleVerses(
        dataList,
        scrollState = rememberLazyListState(),
        headingData = mutableStateOf(BibleModelEntry())
    )
}