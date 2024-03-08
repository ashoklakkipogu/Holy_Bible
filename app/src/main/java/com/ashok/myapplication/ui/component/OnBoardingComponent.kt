package com.ashok.myapplication.ui.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashok.myapplication.R
import com.ashok.myapplication.data.AppConstants
import com.ashok.myapplication.ui.viewmodel.OnBoardingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingComponent(
    onClick: (name: String, language: String) -> Unit
) {
    var userName by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val languages = AppConstants.LANGUAGES
    var selectedOptionText by remember { mutableStateOf(languages[0]) }


    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.onboard),
            contentDescription = "onboard image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(40.dp), color = Color.Transparent
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to Holy Bible",
                    lineHeight = 40.sp,
                    fontSize = 40.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Read bible description",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(40.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = userName,
                    onValueChange = {
                        userName = it
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    label = { Text(text = "Name") },
                    placeholder = { Text(text = "Enter your name") })
                Spacer(modifier = Modifier.height(10.dp))

                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
                    expanded = !expanded
                }) {
                    OutlinedTextField(
                        value = selectedOptionText,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        languages.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it) },
                                onClick = {
                                    selectedOptionText = it
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        if (userName.isBlank()) {
                            Toast.makeText(context, "Please enter name", Toast.LENGTH_SHORT).show()
                        } else
                            onClick.invoke(userName, selectedOptionText)
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Get Started", color = Color.White, fontSize = 16.sp
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun OnBoardingPreview() {
    OnBoardingComponent() { _, _ ->

    }
}