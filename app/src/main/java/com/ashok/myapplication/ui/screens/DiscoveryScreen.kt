package com.ashok.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.component.ButtonView
import com.ashok.myapplication.ui.component.CardView
import com.ashok.myapplication.ui.component.TitleView
import com.ashok.myapplication.ui.theme.BibleTheme
import okhttp3.internal.wait


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(navController: NavController) {
    var layout by remember { mutableStateOf<TextLayoutResult?>(null) }

    BibleTheme {

        Surface(
            modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "ఆదియందు దేవుడు భూమ్యాకాశములను సృజించెను. భూమి నిరాకారముగాను శూన్యముగాను ఉండెను; చీకటి అగాధ జలము పైన కమ్మియుండెను",

                    onTextLayout = {
                        layout = it
                    },
                    modifier = Modifier.drawBehind {

                        layout?.let {
                            val thickness = 5f
                            val dashPath = 15f
                            val spacingExtra = 4f
                            val offsetY = 6f

                            for (i in 0 until it.lineCount) {
                                drawPath(
                                    path = Path().apply {
                                        moveTo(it.getLineLeft(i), it.getLineBottom(i) - spacingExtra + offsetY)
                                        lineTo(it.getLineRight(i), it.getLineBottom(i) - spacingExtra + offsetY)
                                    },
                                    Color.Gray,
                                    style = Stroke(width = thickness,
                                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashPath, dashPath), 0f)
                                    )
                                )
                            }
                        }
                    }
                )
                TitleView(title = "Search by Topic", onClick = {

                })
                ButtonView(color = Color.Red) {}
                Spacer(modifier = Modifier.height(10.dp))
                ButtonView(color = Color.Green) {}
                Spacer(modifier = Modifier.height(10.dp))

                TitleView(title = "New to Faith", onClick = {

                })

                Row {
                    CardView(title = "New to Faith", time = "now", image = R.drawable.onboard) {

                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    CardView(
                        title = "ఆదియందు దేవుడు భూమ్యాకాశములను సృజించెను. భూమి నిరాకారముగాను శూన్యముగాను ఉండెను; చీకటి అగాధ జలము పైన కమ్మియుండెను",
                        time = "12/12/2023",
                        image = R.drawable.banner_1
                    ) {

                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    CardView(
                        title = "Search by Topic", time = "now", image = R.drawable.onboard
                    ) {

                    }
                }

            }


        }
    }
}

@Preview
@Composable
fun DiscoveryScreenPreview() {
    DiscoveryScreen(rememberNavController())
}