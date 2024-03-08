package com.ashok.myapplication.ui.utilities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.ashok.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date

class ShareUtils {

    companion object {
        fun saveBitmapAndGetUri(context: Context, bitmap: Bitmap): Uri? {
            val path: String = context.externalCacheDir.toString() + "/testImg.jpg"
            var out: OutputStream? = null
            val file = File(path)
            try {
                out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return FileProvider.getUriForFile(
                context, context.packageName + ".com.vips.jetcapture.provider", file
            )
        }

        fun shareImageToOthers(context: Context, text: String?, bitmap: Bitmap?) {
            val imageUri: Uri? = bitmap?.let { saveBitmapAndGetUri(context, it) }
            val chooserIntent = Intent(Intent.ACTION_SEND)
            chooserIntent.type = "image/*"
            chooserIntent.putExtra(Intent.EXTRA_TEXT, text)
            chooserIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
            chooserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            try {
                context.startActivity(chooserIntent)
            } catch (ex: Exception) {
            }
        }


        fun shareBitmap(context: Context, bitmap: Bitmap) {
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
            intent.putExtra(Intent.EXTRA_TEXT,"http://play.google.com/store/apps/details?id=" + context.packageName );
            intent.putExtra(Intent.EXTRA_STREAM, myImageFileUri)
            intent.type = "image/png"
            context.startActivity(Intent.createChooser(intent, "Share with"))
        }

        fun saveTempBitmap(context: Context, bitmap: Bitmap) {
            if (isExternalStorageWritable()) {
                saveImage(context, bitmap)
            }
        }
        private fun isExternalStorageWritable(): Boolean {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
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
        fun shareUrl(context: Context, url: String){
            CoroutineScope(Dispatchers.IO).launch {
                val bitmap = getBitmapFromURL(url)
                bitmap?.let { shareBitmap(context, it) }
            }
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
    }


}