package com.ashok.bible.utils

import android.R.id.message
import android.app.Activity
import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.local.BibleDatabase
import org.jsoup.Jsoup
import java.io.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class Utils {

    companion object {

        fun gridRecyclerView(recyclerView: RecyclerView, activity: Context?, spanCount: Int) {
            recyclerView.layoutManager = GridLayoutManager(activity, spanCount)
            recyclerView.isNestedScrollingEnabled = false
            recyclerView.setHasFixedSize(false)
        }

        fun verticalRecyclerView(recyclerView: RecyclerView, activity: Context?) {
            recyclerView.setLayoutManager(LinearLayoutManager(activity))
        }


        fun copyAttachedDatabase(activity: Activity, lan: String) {
            val dbPath: File =
                activity.getDatabasePath(
                    BibleDatabase.DATABASE
                )
            if (dbPath.exists()) {
                return
            }
            dbPath.parentFile.mkdirs()
            try {
                val inputStream: InputStream =
                    activity.getResources()
                        .openRawResource(
                            activity.resources.getIdentifier(
                                lan,
                                "raw",
                                activity.packageName
                            )
                        )
                val output: OutputStream = FileOutputStream(dbPath)
                val buffer = ByteArray(8192)
                var length: Int
                while (inputStream.read(buffer, 0, 8192).also { length = it } > 0) {
                    output.write(buffer, 0, length)
                }
                output.flush()
                output.close()
                inputStream.close()
            } catch (e: IOException) {
                Log.d("Utils", "Failed to open file", e)
                e.printStackTrace()
            }
        }

        fun getStringTimeStampWithDate(): String {
            val data = Date()
            val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            )
            //dateFormat.timeZone = TimeZone.getTimeZone("GMT")
            return dateFormat.format(data)
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

        fun goToPlayStore(activity: Activity) {
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

        fun sendMail(activity: Activity) {
            try {
                val email = Intent(Intent.ACTION_SEND)
                email.putExtra(Intent.EXTRA_EMAIL, arrayOf(AppConstants.EMAIL))
                email.putExtra(Intent.EXTRA_SUBJECT, AppConstants.SUBJECT)
                email.putExtra(Intent.EXTRA_TEXT, message)
                email.type = "message/rfc822"
                activity.startActivity(Intent.createChooser(email, "Choose an Email client :"))

            } catch (e: Exception) { //e.toString();
            }
        }


        fun deleteDb(activity: Activity, selectedLan: String, pref: SharedPreferences) {
            SharedPrefUtils.setLanguage(pref, selectedLan)
            val isDeleted = activity.deleteDatabase(BibleDatabase.DATABASE)
            if (isDeleted) {
                copyAttachedDatabase(activity, selectedLan)
                triggerRebirth(activity)
            }
        }

        fun triggerRebirth(context: Context) {
            val packageManager = context.packageManager
            val intent = packageManager.getLaunchIntentForPackage(context.packageName)
            val componentName = intent!!.component
            val mainIntent = Intent.makeRestartActivityTask(componentName)
            context.startActivity(mainIntent)
            Runtime.getRuntime().exit(0)
        }

        fun shareText(context: Context?, str: String) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, str)

            sendIntent.type = "text/plain"
            context?.startActivity(sendIntent)

        }

        public fun shareBitmap(context: Context, bitmap: Bitmap) {
            //---Save bitmap to external cache directory---//
            //get cache directory
            val cachePath = File(context.externalCacheDir, "camera/")
            cachePath.mkdirs()

            //create png file
            val file = File(cachePath, "bible_verse.png")
            val fileOutputStream: FileOutputStream
            try {
                fileOutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            //---Share File---//
            //get file uri
            val myImageFileUri: Uri = FileProvider.getUriForFile(
                context,
                context.packageName.toString() + ".fileprovider",
                file
            )

            //create a intent
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            //intent.putExtra(Intent.EXTRA_TEXT,"Hey please check this application " + "http://play.google.com/store/apps/details?id=" + context.packageName);
            intent.putExtra(Intent.EXTRA_TEXT,"http://play.google.com/store/apps/details?id=" + context.packageName);
            intent.putExtra(Intent.EXTRA_STREAM, myImageFileUri)
            intent.type = "image/png"
            context.startActivity(Intent.createChooser(intent, "Share with"))
        }

        fun saveTempBitmap(context: Context, bitmap: Bitmap) {
            if (isExternalStorageWritable()) {
                saveImage(context, bitmap)
            }
        }

        private fun saveImage(context: Context, finalBitmap: Bitmap) {
            val root: String = context.getExternalFilesDir(null)!!.absolutePath
            val myDir = File(root + "/" + context.resources.getString(R.string.app_name))
            myDir.mkdirs()
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val name = "bible_verse_$timeStamp.jpg"
            val file = File(myDir, name)
            if (file.exists()) file.delete()
            try {
                val out = FileOutputStream(file)
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        /* Checks if external storage is available for read and write */
        private fun isExternalStorageWritable(): Boolean {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }


        fun copyText(context: Context?, str: String) {
            val clipboard =
                context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("label", str)
            clipboard!!.setPrimaryClip(clip)
        }

        /*fun customEvent(
                firebaseAnalytics: FirebaseAnalytics,
                pref: SharedPreferences,
                book: Int,
                chapter: Int,
                verseCount: Int,
                verse: String,
                bibleId: Int,
                logEvent: String
        ) {
            val params = Bundle()
            params.putString(AppConstants.USER_NAME, SharedPrefUtils.getUserName(pref))
            params.putString(SharedPrefUtils.DEVICE_NAME, SharedPrefUtils.getDeviceName(pref))
            params.putString(SharedPrefUtils.ID, SharedPrefUtils.getId(pref))
            params.putInt(AppConstants.BOOK, book)
            params.putInt(AppConstants.CHAPTER, chapter)
            params.putInt(AppConstants.VERSECOUNT, verseCount)
            params.putString(AppConstants.VERSE, verse)
            params.putInt(AppConstants.BIBLE_ID, bibleId)
            firebaseAnalytics.logEvent(logEvent, params)

        }*/

        fun getCurrentTime(): String {
            val myCalendar = Calendar.getInstance()
            val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            return utcFormat.format(myCalendar.time)

        }

        fun colorCodeByPos(context: Context?, pos: Int): Int {
            val rainbow = context!!.resources.getIntArray(R.array.colorPickerColors)

            return rainbow[pos % rainbow.size]
        }

        fun getBitmapFromView(view: View): Bitmap? {
            val returnedBitmap: Bitmap =
                Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(returnedBitmap)
            val bgDrawable: Drawable = view.background
            if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
            view.draw(canvas)
            return returnedBitmap
        }

        fun html2text(html: String?): String? {
            return Jsoup.parse(html).text()
        }


    }

    /*fun customEvent(
            firebaseAnalytics: FirebaseAnalytics,
            pref: SharedPreferences
    ) {
        val params = Bundle()
        params.putString(AppConstants.USER_NAME, SharedPrefUtils.getUserName(pref))
        params.putString(SharedPrefUtils.DEVICE_NAME, SharedPrefUtils.getDeviceName(pref))
        params.putString(SharedPrefUtils.ID, SharedPrefUtils.getId(pref))
        firebaseAnalytics.logEvent(AppConstants.VERSION, params)

    }*/


}