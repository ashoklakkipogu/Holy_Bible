package com.ashok.myapplication.data.entity

data class Users(
    val data:List<Data>
){
    data class Data(
        val id:Int,
        val email:String,
        val first_name:String,
        val last_name:String,
        val avatar:String,
    )
}

