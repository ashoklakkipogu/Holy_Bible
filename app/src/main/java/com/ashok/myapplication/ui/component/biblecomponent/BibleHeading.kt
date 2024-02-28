package com.ashok.myapplication.ui.component.biblecomponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.myapplication.data.local.entry.BibleModelEntry

@Composable
fun BibleHeading(model: BibleModelEntry) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = model.bibleIndex,
            fontSize = 28.sp,
            lineHeight = 10.sp,
            color = Color.Gray,
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


@Preview
@Composable
fun BibleHeadingPreiew(){
    BibleHeading(BibleModelEntry(bibleIndex = "asasasas", Chapter = 1))
}