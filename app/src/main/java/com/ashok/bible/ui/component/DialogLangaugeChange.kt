package com.ashok.bible.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ashok.bible.data.AppConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DialogLangaugeChange(
    title: String,
    onDismiss: () -> Unit,
    onSelectedLangauge: (String) -> Unit,
) {
    var selectedLangauge by remember {
        mutableStateOf(title)
    }
    val langauges = AppConstants.LANGUAGES
    val coroutineScope = rememberCoroutineScope()

    Dialog(onDismissRequest = { onDismiss.invoke() }) {
        ElevatedCard(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(5.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier
                .padding(8.dp)
                .height(500.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Select Language",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(langauges) { index, model ->
                        val isSelected = selectedLangauge == model
                        TextRoundBox(
                            title = model,
                            isSelected = isSelected
                        ) {
                            selectedLangauge = model
                            coroutineScope.launch {
                                delay(200)
                                onSelectedLangauge.invoke(selectedLangauge)
                                onDismiss.invoke()
                            }

                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun DialogLangaugeChangePrev() {
    DialogLangaugeChange(
        title = "Bible Notes",
        onDismiss = {}){}
}
