package com.example.orangesnote

import androidx.room.Entity
import androidx.room.PrimaryKey

//数据类

@Entity
data class Note (val title:String, val content: String){

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}