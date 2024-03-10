package com.ashok.myapplication.ui.utilities

import android.R.attr.bitmap
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.startActivity
import com.ashok.myapplication.R
import com.ashok.myapplication.data.AppConstants
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar


object BibleUtils {
    /*suspend fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }*/


    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }


    fun getJsonDataFromUrl(url:String):String {
        val obj = URL(url)

        with(obj.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "GET"

            println("\nSending 'GET' request to URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                return response.toString()
                println("GET Response : " + response.toString())
            }
        }
    }


    fun getBibleIndex(context: Context, langaue: String): ArrayList<BibleIndexModelEntry> {
        val url ="https://firebasestorage.googleapis.com/v0/b/bible-8bdba.appspot.com/o/bibledb%2F$langaue%2Fbooks.h?alt=media&token=d0540dcc-8c45-4ca6-8a7d-225cea539319"
        val inputString =  getJsonDataFromUrl(url)
        /*val bufferedReader: BufferedReader =
            context.assets.open("books.h").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }*/
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
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain "
        sendIntent.putExtra(Intent.EXTRA_TEXT, htmlToPlainText(str))
        context?.startActivity(sendIntent)

    }

    fun copyText(context: Context?, str: String) {

        val clipboard =
            context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("label", htmlToPlainText(str))
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

    fun onClickShare(
        context: Context,
        title: String?,
        des: String?
    ) {
        shareText(context, "$des - \n $title")
    }

    fun onClickCopy(
        context: Context,
        title: String?,
        des: String?
    ) {
        copyText(context, "$des - \n $title")
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
    }

    fun htmlToPlainText(html: String): String {
        val regex = "<[^>]*>".toRegex()
        return regex.replace(html, "")
    }

    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection =
                url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun goToPlayStore(activity: Context) {
        val playStoreMarketUrl = "market://details?id="
        val playStoreWebUrl = "https://play.google.com/store/apps/details?id="
        val packageName: String = activity.packageName
        try {
            var intent: Intent =
                activity.packageManager.getLaunchIntentForPackage("com.android.vending")!!
            if (intent != null) {
                val androidComponent = ComponentName(
                    "com.android.vending",
                    "com.google.android.finsky.activities.LaunchUrlHandlerActivity"
                )
                intent.component = androidComponent
                intent.data = Uri.parse(playStoreMarketUrl + packageName)
            } else {
                intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(playStoreMarketUrl + packageName)
                )
            }
            activity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(playStoreWebUrl + packageName))
            activity.startActivity(intent)
        }
    }

    fun shareApp(activity: Activity) {
        try {
            ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setChooserTitle("Chooser title")
                .setText("http://play.google.com/store/apps/details?id=" + activity.packageName)
                .startChooser();

        } catch (e: Exception) { //e.toString();
        }


    }

    fun sendMail(activity: Context) {
        try {
            val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf(AppConstants.EMAIL))
            email.putExtra(Intent.EXTRA_SUBJECT, AppConstants.SUBJECT)
            email.putExtra(Intent.EXTRA_TEXT, android.R.id.message)
            email.type = "message/rfc822"
            activity.startActivity(Intent.createChooser(email, "Choose an Email client :"))

        } catch (e: Exception) { //e.toString();
        }
    }

}

