package com.example.my_lugat.classses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Dictionary(

    @ColumnInfo(name = "dic_id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "dic_name")
    var name: String,
    @ColumnInfo(name = "dic_sinonim")
    var sinonim: String,
    @ColumnInfo(name = "dic_image")
    var image: String,
    @ColumnInfo(name = "dic_liked")
    var isliked: Int = -1,
    @ColumnInfo(name = "categ")
    var categname: String,
    @ColumnInfo(name = "dic_category")
    var categ: Int = 0,

    ) : Serializable