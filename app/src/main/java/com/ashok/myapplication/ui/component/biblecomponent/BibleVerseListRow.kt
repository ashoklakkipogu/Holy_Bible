package com.ashok.myapplication.ui.component.biblecomponent

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.paging.Pager
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ashok.myapplication.R
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.ui.utilities.BibleUtils.getInlineIcon

@Composable
fun BibleVerseList(
    biblePager: Pager<Int, BibleModelEntry>,
    state: LazyListState = rememberLazyListState(),
    onItemClick: (BibleModelEntry) -> Unit,
    onHeadingTitle: (BibleModelEntry) -> Unit
) {
    val pager = remember { biblePager }
    val bibleData = pager.flow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        state
    ) {
        items(
            count = bibleData.itemCount,
            key = bibleData.itemKey(BibleModelEntry::id),
            contentType = bibleData.itemContentType { "Articles" }
        ) { index: Int ->
            val model: BibleModelEntry = bibleData[index] ?: return@items

            onHeadingTitle.invoke(model)
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
                        model = model, onClick = onItemClick
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }

            }
        }

    }
}

@Composable
fun BibleVerse(
    model: BibleModelEntry,
    onClick: (BibleModelEntry) -> Unit,
) {
    Box(modifier = Modifier
        .clickable {
            onClick.invoke(model)
        }
        .fillMaxWidth(), contentAlignment = Alignment.Center) {

        Text(
            text = buildAnnotatedString {
                if (model.isBookMark || model.isNote) appendInlineContent("icon", "icon")
                withStyle(
                    style = SpanStyle(
                        baselineShift = BaselineShift.Superscript,
                        color = colorResource(id = R.color.colorAccent),
                        fontSize = 16.sp,

                        )
                ) {
                    append("${model.Versecount} ")
                }
                withStyle(
                    style = SpanStyle(
                        textDecoration = if (model.selectedBackground == "underline") TextDecoration.Underline else TextDecoration.None,
                        fontSize = 18.sp
                    )

                ) {
                    append("${model.verse} ")
                }

            },
            inlineContent = getInlineIcon("icon", model),
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
            style = if (model.selectedBackground.isNotBlank() && model.selectedBackground != "underline") TextStyle(
                background = Color(model.selectedBackground.toColorInt())
            ) else TextStyle.Default
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
    dataList.add(data)/*bibleVerses(
        bibleDataList = dataList,
        scrollState = rememberLazyListState(),
        headingData = mutableStateOf(BibleModelEntry())
    )*/
    BibleVerse(model = data, onClick = {})
}
