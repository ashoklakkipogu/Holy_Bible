package com.ashok.myapplication.ui.utilities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import com.ashok.myapplication.R
import com.ashok.myapplication.data.AppConstants
import com.ashok.myapplication.data.local.dao.BibleIndexDao
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import java.io.BufferedReader
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar

object BibleUtils {
    suspend fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getBibleIndex(context: Context, langaue: String): ArrayList<BibleIndexModelEntry> {
        val bufferedReader: BufferedReader =
            context.assets.open("books.h").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        println(inputString)
        val bibleIndex = inputString.replace("Books[] = ", "").replace("{", "").replace("};", "")
            .replace("\"", "")
        val data = arrayListOf<BibleIndexModelEntry>()
        bibleIndex.split(",").forEachIndexed { i, item ->
            val obj = BibleIndexModelEntry()
            val index = i + 1
            obj.chapter = item.trim()
            obj.chapter_id = index
            obj.langauge = langaue
            obj.bibleLangIndex = "$langaue-$index"

            //println(item.trim())
            data.add(obj)
        }
        return data
    }

    fun shareText(context: Context?, str: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, str)

        sendIntent.type = "text/plain"
        context?.startActivity(sendIntent)

    }

    fun copyText(context: Context?, str: String) {
        val clipboard =
            context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("label", str)
        clipboard!!.setPrimaryClip(clip)
    }

    fun getCurrentTime(): String {
        val myCalendar = Calendar.getInstance()
        val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        return utcFormat.format(myCalendar.time)

    }

    public fun getUtcToDDMMYYYYHHMMA(utcDate: String): String {
        var outputDate = ""
        try {
            if (utcDate.isNotEmpty()) {
                val date =
                    SimpleDateFormat(AppConstants.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS_Z).parse(
                        utcDate
                    )
                outputDate =
                    SimpleDateFormat(AppConstants.DATE_FORMAT_dd_MM_yyyy_hh_mm_a).format(date)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return outputDate
    }

    fun getInlineIcon(myId: String, model: BibleModelEntry) = mapOf(
        Pair(myId, InlineTextContent(
            Placeholder(
                width = 16.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextTop
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (model.isBookMark) Icon(
                    painter = painterResource(id = R.drawable.ic_bookmarks_24dp),
                    contentDescription = null,
                    tint = Color.Red
                )
                if (model.isNote) Icon(
                    painter = painterResource(id = R.drawable.ic_notes_24dp),
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        })
    )

    fun shareBibleUrl(
        selectedBible: BibleModelEntry,
        context: Context
    ) {
        val bibleIndex =
            "${selectedBible.bibleIndex} ${selectedBible.Chapter}:${selectedBible.Versecount}"
        val verse = selectedBible.verse
        val strBuilder = StringBuilder()
        strBuilder.append("$bibleIndex $verse")
        strBuilder.append("\n");
        strBuilder.append("http://play.google.com/store/apps/details?id=${context.packageName}");
        strBuilder.append("\n");
        shareText(context, strBuilder.toString())
    }

    fun copyBibleVerse(
        selectedBible: BibleModelEntry,
        context: Context
    ) {
        val bibleIndex =
            "${selectedBible.bibleIndex} ${selectedBible.Chapter}:${selectedBible.Versecount}"
        val verse = selectedBible.verse
        val str = "$verse \n  $bibleIndex"
        copyText(context, str)
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
    }

}

