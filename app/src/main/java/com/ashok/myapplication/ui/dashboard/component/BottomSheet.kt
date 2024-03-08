package com.ashok.myapplication.ui.dashboard.component

import android.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashok.myapplication.ui.component.ButtonComponent
import com.ashok.myapplication.ui.component.GridImages
import com.ashok.myapplication.ui.component.circleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    selectedBible: String = "",
    isBookMark: Boolean = false,
    isNote: Boolean = false,
    onDismiss: () -> Unit,
    onCircleColor: (String) -> Unit,
    onButtonClick: (String) -> Unit,
    onGridImgClick: (Int) -> Unit,
    onSoundClick: () -> Unit
) {
    val colorList = arrayListOf(
        "underline",
        "#FFCDD2",
        "#F8BBD0",
        "#E1BEE7",
        "#B39DDB",
        "#90CAF9",
        "#80DEEA",
        "#80CBC4",
        "#E6EE9C",
        "#FFB74D",
        "#FFEB3B",
        "#FF5722",
        "#8D6E63,",
        "#FFFFFF",
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
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(35.dp),
                        onClick = {
                            onSoundClick.invoke()
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
                Spacer(modifier = Modifier.height(10.dp))
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
    BottomSheet(onDismiss = { }, onCircleColor = {}, onButtonClick = {}, onGridImgClick = {}) {}
}