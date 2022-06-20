package com.ashok.bible.data.remote.firebase

interface FirebaseImageUploadListener {
    fun uploadSuccess(url:String);
    fun uploadError();
}