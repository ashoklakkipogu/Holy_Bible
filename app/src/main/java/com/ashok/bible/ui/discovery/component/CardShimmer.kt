package com.ashok.bible.ui.discovery.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardShimmer() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    ShimmerItem(brush = brush)
}

@Composable
fun ShimmerItem(brush: Brush) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Spacer(
                modifier = Modifier
                    .height(15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(fraction = 0.7f)
                    .background(brush)
            )
            Spacer(
                modifier = Modifier
                    .height(15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(fraction = 0.2f)
                    .background(brush)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            repeat(6) {
                Column {
                    Spacer(
                        modifier = Modifier
                            .size(120.dp, 130.dp)
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Spacer(
                        modifier = Modifier
                            .size(120.dp, 10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Spacer(
                        modifier = Modifier
                            .size(100.dp, 10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(brush)
                    )

                }

            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun CardShimmerPreview() {
    CardShimmer()

}