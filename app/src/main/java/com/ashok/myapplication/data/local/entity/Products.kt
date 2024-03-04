package com.ashok.myapplication.data.local.entity

data class Products(
    val data:List<Data> = emptyList()
){
    data class Data(
        val id:Int,
        val name:String,
        val color:String,
        val pantone_value:String,

    )
}

