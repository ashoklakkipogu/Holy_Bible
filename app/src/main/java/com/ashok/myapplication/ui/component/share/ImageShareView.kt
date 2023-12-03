package com.ashok.myapplication.ui.component.share

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.toColorInt
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.component.ButtonView
import com.ashok.myapplication.ui.component.circleColor
import com.ashok.myapplication.ui.utilities.ShareUtils
import dev.shreyaspatil.capturable.controller.CaptureController
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlin.math.roundToInt
import dev.shreyaspatil.capturable.*


@Composable
fun ImageShareView(
    image: Int, captureController: CaptureController, onCaptured: (ImageBitmap?, Throwable?) -> Unit
) {
    val context = LocalContext.current
    var textAlign by remember {
        mutableStateOf(TextAlign.Center)
    }
    var textPos by remember {
        mutableStateOf(Alignment.Center)
    }
    var textLineHeight by remember {
        mutableIntStateOf(16)
    }
    var textSpacing by remember {
        mutableIntStateOf(0)
    }
    var textSize by remember {
        mutableIntStateOf(14)
    }
    var textAlpha by remember {
        mutableFloatStateOf(1.0f)
    }
    var textColor by remember {
        mutableStateOf("")
    }

    var textUnder by remember {
        mutableStateOf("")
    }
    var bgAlpha by remember {
        mutableFloatStateOf(1.0f)
    }
    var bgBlur by remember {
        mutableIntStateOf(0)
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
                .background(Color.White)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {
                DraggableTextLowLevel(
                    image = image,
                    textAlign = textAlign,
                    textPos = textPos,
                    textLineHeight = textLineHeight,
                    textSpace = textSpacing,
                    textSize = textSize,
                    textAlpha = textAlpha,
                    textColor = textColor,
                    textUnder = textUnder,
                    bgAlpha = bgAlpha,
                    bgBlur = bgBlur,
                    captureController = captureController,
                    onCaptured = { bitmap, error ->
                        onCaptured.invoke(bitmap, error)
                    }
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(0.3f),


                ) {
                BottomView(onTextAlign = {
                    textAlign = it
                }, onTextPos = {
                    textPos = it
                }, onTextLineHeight = {
                    if (it == 1) {
                        textLineHeight = (textLineHeight - 1)
                    } else if (it == 2) {
                        textLineHeight = (textLineHeight + 1)
                    }

                }, onTextSpace = {
                    if (it == 1) {
                        if (textSpacing > 0) textSpacing = (textSpacing - 1)
                    } else if (it == 2) {
                        textSpacing = (textSpacing + 1)

                    }
                }, onTextSize = {
                    textSize = it
                    textLineHeight = (textSize + 1)
                }, onTextAlpha = {
                    textAlpha = it
                }, onTextColor = {
                    if (it == "underline") {
                        textUnder = if (textUnder != it) it else ""

                    } else {
                        textColor = if (textColor != it) it else ""
                    }
                }, onBgAlpha = {
                    bgAlpha = it
                }, onBgBlur = {
                    bgBlur = it
                })
            }

        }
    }
}

@Composable
fun BottomView(
    onTextAlign: (TextAlign) -> Unit,
    onTextPos: (Alignment) -> Unit,
    onTextLineHeight: (Int) -> Unit,
    onTextSpace: (Int) -> Unit,
    onTextSize: (Int) -> Unit,
    onTextAlpha: (Float) -> Unit,
    onTextColor: (String) -> Unit,
    onBgAlpha: (Float) -> Unit,
    onBgBlur: (Int) -> Unit,

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
        "#8D6E63"
    )
    val state = rememberScrollState()
    //LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(state)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                ThreeButtonView(
                    title = "Text Align",
                    icon1 = R.drawable.text_align_left,
                    icon2 = R.drawable.text_align_center,
                    icon3 = R.drawable.text_align_right
                ) {
                    when (it) {
                        1 -> {
                            onTextAlign.invoke(TextAlign.Start)
                        }

                        2 -> {
                            onTextAlign.invoke(TextAlign.Center)
                        }

                        3 -> {
                            onTextAlign.invoke(TextAlign.End)
                        }
                    }
                }
                ThreeButtonView(
                    title = "Text Position",
                    icon1 = R.drawable.text_pos_top,
                    icon2 = R.drawable.text_pos_center,
                    icon3 = R.drawable.text_pos_bottom
                ) {
                    when (it) {
                        1 -> {
                            onTextPos.invoke(Alignment.TopStart)
                        }

                        2 -> {
                            onTextPos.invoke(Alignment.Center)
                        }

                        3 -> {
                            onTextPos.invoke(Alignment.BottomEnd)
                        }
                    }

                    //onTextAlign.invoke(TextAlign.Start)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                TwoButtonView(
                    title = "Line height"
                ) {
                    onTextLineHeight.invoke(it)
                }
                TwoButtonView(
                    title = "Letter spacing"
                ) {
                    onTextSpace.invoke(it)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            SlideBarView(title = "Font Size", valueRange = 10f..30f, onValueChange = {
                onTextSize.invoke(it)
            })
            Spacer(modifier = Modifier.height(8.dp))
            circleColor(colors = colorList) {
                onTextColor.invoke(it)
            }

            Spacer(modifier = Modifier.height(8.dp))
            SlideBarView(title = "Opacity", valueRange = 0f..10f, onValueChange = {
                onTextAlpha.invoke((10 - it) / 10.toFloat())
            })

            Spacer(modifier = Modifier.height(8.dp))
            SlideBarView(title = "Brightness", valueRange = 0f..10f, onValueChange = {
                onBgAlpha.invoke((10 - it) / 10.toFloat())
            })

            Spacer(modifier = Modifier.height(8.dp))
            SlideBarView(title = "Blur", valueRange = 0f..30f, onValueChange = {
                onBgBlur.invoke(it)
            })
        }
    }

}


@Composable
private fun DraggableTextLowLevel(
    image: Int,
    textAlign: TextAlign,
    textPos: Alignment,
    textLineHeight: Int,
    textSpace: Int,
    textSize: Int,
    textAlpha: Float,
    textColor: String,
    textUnder: String,
    bgAlpha: Float,
    bgBlur: Int,
    captureController: CaptureController,
    onCaptured: (ImageBitmap?, Throwable?) -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    val context = LocalContext.current

    Log.i("textAlpha", "textAlpha...........$textAlpha")
    Capturable(
        controller = captureController,
        onCaptured = { bitmap, error ->
            onCaptured.invoke(bitmap, error)
        }

    ) {
        Box(
            modifier = Modifier.fillMaxSize()

        ) {
            Image(

                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .blur(bgBlur.dp)
                    .alpha(bgAlpha)

            )
            Text(
                text = context.resources.getString(R.string.app_name),
                color = Color.White,
                modifier = Modifier
                    .padding(10.dp)
                    .align(BottomEnd)
                    .alpha(0.6f),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Text(text = "In the sweat of your face shall you eat bread,till " + "you return unto the ground; for out of it were you taken: " + "for dust you are, and unto dust shall you return.",
                fontSize = textSize.sp,
                lineHeight = textLineHeight.sp,
                textDecoration = if (textUnder.isNotBlank() && textUnder == "underline") TextDecoration.Underline else TextDecoration.None,
                color = if (textColor.isNotBlank()) Color(textColor.toColorInt()) else Color.White,
                letterSpacing = if (textSpace > 0) textSpace.sp else TextUnit.Unspecified,
                modifier = Modifier
                    .padding(10.dp)
                    .align(textPos)
                    .alpha(textAlpha)
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                        }
                    },
                textAlign = textAlign


            )
        }
    }

}

@Preview
@Composable
fun ImageShareViewPreview() {
    ImageShareView(
        image = R.drawable.banner_1,
        captureController = rememberCaptureController(),
        onCaptured = { bitmap, error ->

        })
}
