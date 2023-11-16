package com.ashok.myapplication.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.R
import com.ashok.myapplication.data.entity.Products
import com.ashok.myapplication.ui.screens.HomeScreen

@Composable
fun bibleVerses(response: List<Products.Data>, scrollState: LazyListState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
        state = scrollState
    ) {
        items(response) { model ->
            ListItem(model = model)
        }
    }
}

@Composable
fun ListItem(model: Products.Data) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ఆదికాండము",
                    fontSize = 20.sp,
                    lineHeight = 10.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "1",
                    fontSize = 32.sp,
                    lineHeight = 10.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
            }

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                baselineShift = BaselineShift.Superscript,
                                color = colorResource(id = R.color.colorAccent),
                                fontSize = 14.sp,

                                )
                        ) {
                            append("1 ")
                        }
                        append("ఆదియందు దేవుడు భూమ్యాకాశములను సృజించెను. భూమి నిరాకారముగాను శూన్యముగాను ఉండెను; చీకటి అగాధ జలము పైన కమ్మియుండెను; దేవుని ఆత్మ జలములపైన అల్లాడుచుండెను.")
                    },
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                baselineShift = BaselineShift.Superscript,
                                color = colorResource(id = R.color.colorAccent),
                                fontSize = 14.sp,

                                )
                        ) {
                            append("2 ")
                        }
                        append("ఆదియందు దేవుడు భూమ్యాకాశములను సృజించెను. భూమి నిరాకారముగాను శూన్యముగాను ఉండెను; చీకటి అగాధ జలము పైన కమ్మియుండెను; దేవుని ఆత్మ జలములపైన అల్లాడుచుండెను.")
                    },
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                )
            }
        }

    }
}


@Preview
@Composable
fun BibleVersesPreview() {
    val dataList = ArrayList<Products.Data>()
    val data = Products.Data(1, "Test", "Test", "Test")
    dataList.add(data)
    bibleVerses(dataList, scrollState = rememberLazyListState())
}