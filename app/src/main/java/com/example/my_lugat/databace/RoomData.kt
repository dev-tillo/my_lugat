package com.example.my_lugat.databace

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.my_lugat.classses.Category
import com.example.my_lugat.classses.Dictionary

@Database(entities = [Category::class, Dictionary::class], version = 1)
abstract class RoomData : RoomDatabase() {

    abstract fun dicDao(): DicDao

    companion object {
        private var appDatabase: RoomData? = null

        @Synchronized
        fun getInstance(context: Context): RoomData {
            if (appDatabase == null) {
                appDatabase =
                    Room.databaseBuilder(context, RoomData::class.java, "lugat_db")
                        .allowMainThreadQueries()
                        .build()
            }
            return appDatabase!!
        }
    }

}