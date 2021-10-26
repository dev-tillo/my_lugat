package com.example.my_lugat.classses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String
    ) : Serializable