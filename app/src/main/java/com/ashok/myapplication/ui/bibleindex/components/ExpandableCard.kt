package com.ashok.myapplication.ui.bibleindex.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.ui.bibleindex.BibleIndexViewModel
import com.ashok.myapplication.ui.viewmodel.HomeViewModel


@Composable
fun ExpandableCard(
    data: BibleIndexModelEntry,
    viewModel: BibleIndexViewModel,
    onClick: (BibleIndexModelEntry) -> Unit,
    onClickIndex: (Int) -> Unit
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
            Text(
                modifier = Modifier.weight(6f),
                text = data.chapter,
                fontSize = 16.sp,
                fontWeight = if (expandedState) FontWeight.Bold else FontWeight.Normal
            )
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
            var chaptersList = viewModel.chaptersList
            if (chaptersList.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = Color.LightGray,
                    )
                }

            } else
                NonLazyVerticalGrid(
                    columns = 6,
                    data = chaptersList
                ) { item ->
                    /*chaptersList.map {
                        {*/
                    IndexView(title = item) {
                        onClickIndex.invoke(it)
                    }
                    /* }
                 }*/

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
        viewModel = hiltViewModel(),
        onClick = {},
        onClickIndex = {}
    )
}


