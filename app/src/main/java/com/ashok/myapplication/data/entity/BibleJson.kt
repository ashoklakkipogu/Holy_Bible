package com.ashok.myapplication.data.entity

import com.google.gson.annotations.SerializedName

data class BibleJson(
    @SerializedName("Book")
    val book: List<Book>
)

data class Book(
    @SerializedName("Chapter")
    val chapter: List<Chapter>
)

data class Chapter(
    @SerializedName("Verse")
    val verse: List<Verse>
)

data class Verse(
    @SerializedName("Verseid")
    val verseid: String,

    @SerializedName("Verse")
    val Verse: String
)
