package com.ashok.bible.ui.discovery.model

import androidx.compose.ui.graphics.Color
import com.ashok.bible.ui.utilities.RandomColors

data class ImageGrid(
    var image: String? = null,
    var drawable: Int? = null,
    var title: String? = null,
    var des: String? = null,
    var color: Color? = null,
)
