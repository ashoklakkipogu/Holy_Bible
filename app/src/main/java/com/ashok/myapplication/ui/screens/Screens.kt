package com.ashok.myapplication.ui.screens

import android.graphics.Paint
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.res.painterResource
import com.ashok.myapplication.R
import androidx.compose.ui.res.vectorResource


const val SCROLL_ID = "scrollId"

sealed class Screens(var router: String, var title: String, @DrawableRes val icon: Int) {
    /*object Bible : Screens("Bible/{$SCROLL_ID}", "Bible", R.drawable.menu_book){
        fun passId(id:Int):String{
            return "Bible/$id"
        }
    }*/
    object Bible : Screens("Bible", "Bible", R.drawable.menu_book)
    object Bookmark : Screens("Note", "Note", R.drawable.notes_24)
    object Lyrics : Screens("Lyrics", "Lyrics", R.drawable.lyrics_24)
    object Discovery : Screens("Discovery", "Discovery", R.drawable.search_24)
    object More : Screens("More", "More", R.drawable.more_24)

    object DashboardRoute : Screens("dashboard", "dashboard", R.drawable.menu_book)

}