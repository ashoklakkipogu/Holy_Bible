package com.ashok.myapplication.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottomSheet(
    onDismiss: () -> Unit,
    onCircleColor: (String) -> Unit,
    onButtonClick: (String) -> Unit,
    onGridImgClick: (Int) -> Unit
) {
    val colorList = arrayListOf(
        "underline", "#FFCDD2", "#F8BBD0", "#E1BEE7", "#B39DDB",
        "#90CAF9", "#80DEEA", "#80CBC4", "#E6EE9C", "#FFB74D", "#FFEB3B", "#FF5722", "#8D6E63"
    )
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                circleColor(colors = colorList) { onCircleColor.invoke(it) }
                ButtonComponent {
                    onButtonClick.invoke(it)
                }
                GridImages {
                    onGridImgClick.invoke(it)
                }
            }

        }
    )

}


@Preview
@Composable
fun bottomSheetPreview() {
    bottomSheet(onDismiss = { }, onCircleColor = {}, onButtonClick = {}, onGridImgClick = {})
}