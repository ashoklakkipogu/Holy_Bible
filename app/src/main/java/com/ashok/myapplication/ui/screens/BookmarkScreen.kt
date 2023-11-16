package com.ashok.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.ui.component.BibleWordListView
import com.ashok.myapplication.ui.theme.BibleTheme

@Composable
fun BookmarkScreen(navController: NavController) {
    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                BibleWordListView(
                    title = "ఆదియందు దేవుడు భూమ్యాకాశములను సృజించెను. భూమి నిరాకారముగాను శూన్యముగాను ఉండెను; చీకటి అగాధ జలము పైన కమ్మియుండెను",
                    subTitle = "ఆదియందు 1:2",
                    dividerColor = Color.Black,
                    heading = "You added a Note",
                    timings = "2 weeks ago",
                    isVisibleHeading = true
                ) {

                }
                BibleWordListView(
                    title = "ఆదియందు దేవుడు భూమ్యాకాశములను సృజించెను. భూమి నిరాకారముగాను శూన్యముగాను ఉండెను; చీకటి అగాధ జలము పైన కమ్మియుండెను",
                    subTitle = "ఆదియందు 1:2",
                    dividerColor = Color.Black,
                    heading = "You added a Note",
                    timings = "2 weeks ago",
                    isVisibleHeading = true
                ) {

                }
                BibleWordListView(
                    title = "ఆదియందు దేవుడు భూమ్యాకాశములను సృజించెను. భూమి నిరాకారముగాను శూన్యముగాను ఉండెను; చీకటి అగాధ జలము పైన కమ్మియుండెను",
                    subTitle = "ఆదియందు 1:2",
                    dividerColor = Color.Black,
                    heading = "You added a Note",
                    timings = "2 weeks ago",
                    isVisibleHeading = true
                ) {

                }
            }




        }
    }
}


@Preview
@Composable
fun BookmarkScreenPreview() {
    BookmarkScreen(rememberNavController())
}