package com.ashok.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.component.MenuItemView
import com.ashok.myapplication.ui.theme.BibleTheme

@Composable
fun ProfileScreen(navController: NavController) {
    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val gradient =
                Brush.horizontalGradient(listOf(Color(0xFF28D8A3), Color(0xFF00BEB2)))
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(gradient)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 50.dp, horizontal = 20.dp)
                    ) {
                        Text(
                            text = "Ashok Lakkipogu",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_note_24dp),
                                contentDescription = "more",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(12.dp)
                                    .padding(end = 2.dp)
                            )
                            Text(
                                text = "Telugu bible",
                                fontSize = 12.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }

                    }

                }
                Column(
                ) {
                    MenuItemView("Language Change", R.drawable.ic_language) {}
                    MenuItemView("Share App", R.drawable.ic_share) {}
                    MenuItemView("Feedback", R.drawable.ic_feedback) {}
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun profileView() {
    ProfileScreen(rememberNavController())
}