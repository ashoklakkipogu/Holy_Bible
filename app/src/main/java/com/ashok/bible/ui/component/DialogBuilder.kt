package com.ashok.bible.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun InputDialogView(
    title: String,
    placeholder: String,
    onDismiss: () -> Unit,
    onEnteredText: (String) -> Unit
) {
    val context = LocalContext.current
    var searchedFood by remember {
        mutableStateOf("")
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        ElevatedCard(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(5.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier.padding(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                Modifier
                    .background(Color.White)
            ) {

                Text(
                    text = title,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 20.sp
                )

                OutlinedTextField(
                    value = searchedFood,
                    onValueChange = { searchedFood = it }, modifier = Modifier.padding(8.dp),
                    label = { Text(placeholder) }
                )

                Row {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Cancel")
                    }


                    Button(
                        onClick = {
                            onEnteredText.invoke(searchedFood)
                            //Toast.makeText(context, searchedFood, Toast.LENGTH_SHORT).show()
                            onDismiss()
                        },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Save")
                    }
                }


            }
        }
    }
}

@Preview
@Composable
fun InputDialogViewPrev() {
    InputDialogView(
        title = "Bible Notes",
        placeholder = "Note text",
        onDismiss = {},
        onEnteredText = {})
}
