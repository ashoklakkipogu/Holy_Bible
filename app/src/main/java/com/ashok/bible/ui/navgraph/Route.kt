package com.ashok.bible.ui.navgraph

import androidx.annotation.DrawableRes
import com.ashok.bible.R


const val BOOK_ID = "BOOK_ID"
const val CHAPTER_ID = "CHAPTER_ID"
const val LYRIC_OBJ = "LYRIC_OBJ"
const val IMAGE_GRID = "IMAGE_GRID"
const val QUOTES_OBJ = "QUOTES_OBJ"

sealed class Route(var router: String, var title: String, @DrawableRes val icon: Int) {
    /*object Bible : Screens("Bible?scrollId={$SCROLL_ID}", "Bible", R.drawable.menu_book){
        fun passId(id:Int):String{
            return "Bible?scrollId=$id"
        }
    }*/
    object Bible : Route("Bible", "Bible", R.drawable.menu_book)
    //object Bible : Screens("Bible?scrollId={$SCROLL_ID}", "Bible", R.drawable.menu_book)
    object Bookmark : Route("Note", "Note", R.drawable.notes_24)
    object Lyrics : Route("Lyrics", "Lyrics", R.drawable.lyrics_24)

    object LyricDetails : Route("LyricDetails", "LyricDetails", R.drawable.lyrics_24)

    object Discovery : Route("Discovery", "Discovery", R.drawable.search_24)
    object DiscoveryGridDetails : Route("DiscoveryGridDetails", "DiscoveryGridDetails", R.drawable.search_24)
    object DiscoveryStoryDetails : Route("DiscoveryStoryDetails", "DiscoveryStoryDetails", R.drawable.search_24)
    object DiscoveryTopicDetails : Route("DiscoveryTopicDetails", "DiscoveryTopicDetails", R.drawable.search_24)
    object More : Route("More", "More", R.drawable.more_24)

    object DashboardRoute : Route("dashboard", "dashboard", R.drawable.menu_book)
    object BibleIndex : Route("bibleIndex", "bibleIndex", R.drawable.menu_book)
    //object BibleIndexDetails : Route("bibleIndexDetails?bookId={$BOOK_ID}&chapterId={$CHAPTER_ID}", "bibleIndexDetails", R.drawable.round_bible)

    object BibleIndexDetails: Route("bibleIndexDetails/{$BOOK_ID}/{$CHAPTER_ID}", "bibleIndexDetails", R.drawable.menu_book) {
        fun getFullRoute(bookId: Int, chapterId: Int): String {
            return "bibleIndexDetails/$bookId/$chapterId"
        }
    }
}