package com.ashok.bible.data.remote.firebase

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.*


class Firebase {
    companion object{
        public fun uploadImage(activity: Activity?, mStorageRef:StorageReference, uri: Uri, listener: FirebaseImageUploadListener){
            val fileName = UUID.randomUUID().toString()
            val riversRef: StorageReference = mStorageRef.child("images/$fileName.jpg")
            val imageByte = activity?.let { decodeUriToBitmap(it, uri) }
            imageByte?.let {
                riversRef.putBytes(it)
                    .addOnSuccessListener { taskSnapshot -> // Get a URL to the uploaded content
                        val url =
                            "https://firebasestorage.googleapis.com/v0/b/bible-8bdba.appspot.com/o/images%2F$fileName.jpg?alt=media"
                        listener.uploadSuccess(url)
                    }
                    .addOnFailureListener {
                        Log.e("error", "error...."+it);
                        listener.uploadError()
                    }
            }
        }
        fun decodeUriToBitmap(mContext: Context, sendUri: Uri): ByteArray? {
            var data: ByteArray? = null
            try {
                try {
                    val imageStream = mContext.contentResolver.openInputStream(sendUri)
                    var bitmap = BitmapFactory.decodeStream(imageStream)
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    data = baos.toByteArray()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return data
        }

    }

}