package com.ashok.myapplication.ui.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.painterResource
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.component.share.ImageShareView
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.utilities.ShareUtils
import com.ashok.myapplication.ui.utilities.ShareUtils.Companion.saveTempBitmap
import dagger.hilt.android.AndroidEntryPoint
import dev.shreyaspatil.capturable.controller.rememberCaptureController


@AndroidEntryPoint
class ShareImageActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedImg = intent.getIntExtra("selected_image", R.drawable.place_holder)
        val selectedBibleVerse = intent.getStringExtra("selected_title")



        setContent {
            BibleTheme {
                val captureController = rememberCaptureController()
                var viewBitmap: ImageBitmap? by remember { mutableStateOf(null) }
                var isShare by remember {
                    mutableStateOf(false)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text("Create image")
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        finish()
                                    }) {
                                        Icon(Icons.AutoMirrored.Default.ArrowBack, "ArrowBack")
                                    }
                                },
                                actions = {
                                    IconButton(onClick = {
                                        isShare = false
                                        captureController.capture()
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.icon_download),
                                            contentDescription = null
                                        )
                                    }
                                    IconButton(onClick = {
                                        isShare = true
                                        captureController.capture()
                                    }) {
                                        Icon(Icons.Filled.Share, contentDescription = null)
                                    }
                                },
                                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White)
                            )


                        }) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            ImageShareView(
                                bibleVerse = selectedBibleVerse,
                                image = selectedImg,
                                captureController = captureController
                            ) { bitmap, error ->
                                if (bitmap != null) {
                                    viewBitmap = bitmap
                                }

                                if (error != null) {
                                    // Error occurred. Handle it!
                                }
                            }
                        }
                    }
                    viewBitmap?.let {
                        Log.i("bitmap......", "bitmap.............${it.asAndroidBitmap()}")
                        if (isShare) {
                            ShareUtils.shareBitmap(this, it.asAndroidBitmap())
                        } else {
                            saveTempBitmap(this, it.asAndroidBitmap())
                            Toast.makeText(
                                this,
                                "Image has been downloaded successfully. Android/data/com.ashok.bible/files/${resources.getString(R.string.app_name)}",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        viewBitmap = null
                    }
                }
            }
        }
    }

}
