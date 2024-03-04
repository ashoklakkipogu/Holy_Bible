package com.ashok.myapplication.ui.dashboard.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashok.myapplication.ui.component.ButtonComponent
import com.ashok.myapplication.ui.component.GridImages
import com.ashok.myapplication.ui.component.circleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottomSheet(
    selectedBible: String = "",
    isBookMark: Boolean = false,
    isNote: Boolean = false,
    onDismiss: () -> Unit,
    onCircleColor: (String) -> Unit,
    onButtonClick: (String) -> Unit,
    onGridImgClick: (Int) -> Unit
) {
    val colorList = arrayListOf(
        "underline", "#FFCDD2", "#F8BBD0", "#E1BEE7", "#B39DDB",
        "#90CAF9", "#80DEEA", "#80CBC4", "#E6EE9C", "#FFB74D", "#FFEB3B", "#FF5722", "#8D6E63,", "#FFFFFF",
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
                circleColor(colors = colorList, selectedBible) { onCircleColor.invoke(it) }
                ButtonComponent(isBookMark = isBookMark, isNote = isNote) {
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