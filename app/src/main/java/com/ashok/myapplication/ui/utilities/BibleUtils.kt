package com.ashok.myapplication.ui.utilities

import android.content.Context
import androidx.compose.ui.util.fastForEach
import com.ashok.myapplication.R
import com.ashok.myapplication.data.local.dao.BibleIndexDao
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import java.io.BufferedReader
import java.io.IOException

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
}