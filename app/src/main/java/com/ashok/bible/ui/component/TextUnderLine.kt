package com.ashok.bible.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextUnderLine(){
    var layout by remember { mutableStateOf<TextLayoutResult?>(null) }
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
                            moveTo(
                                it.getLineLeft(i),
                                it.getLineBottom(i) - spacingExtra + offsetY
                            )
                            lineTo(
                                it.getLineRight(i),
                                it.getLineBottom(i) - spacingExtra + offsetY
                            )
                        },
                        Color.Gray,
                        style = Stroke(
                            width = thickness,
                            pathEffect = PathEffect.dashPathEffect(
                                floatArrayOf(
                                    dashPath,
                                    dashPath
                                ), 0f
                            )
                        )
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun TextUnderLinePreview(){
    TextUnderLine()
}