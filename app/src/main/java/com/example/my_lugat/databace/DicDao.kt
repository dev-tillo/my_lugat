package com.example.my_lugat.databace

import androidx.room.*
import com.example.my_lugat.classses.Category
import com.example.my_lugat.classses.Dictionary
import io.reactivex.Flowable


@Dao
interface DicDao {

    @Insert
    fun addLugat(dictionary: Dictionary)

    @Insert
    fun addCategory(category: Category)

    @Update
    fun editLugat(dictionary: Dictionary)

    @Update
    fun editCategory(category: Category)

    @Delete
    fun deletLugat(dictionary: Dictionary)

    @Delete
    fun deleteCategory(category: Category)

    @Query("select *from category")
    fun getAllCategory(): Flowable<List<Category>>

    @Query("select *from dictionary")
    fun getAllLugat(): Flowable<List<Dictionary>>

    @Query("select * from category")
    fun getAllCategoryList(): List<Category>

}